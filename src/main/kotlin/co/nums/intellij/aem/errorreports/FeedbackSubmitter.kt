package co.nums.intellij.aem.errorreports

import co.nums.intellij.aem.service.PluginInfoProvider
import com.intellij.openapi.diagnostic.SubmittedReportInfo
import com.intellij.openapi.diagnostic.SubmittedReportInfo.SubmissionStatus
import org.eclipse.egit.github.core.*

object FeedbackSubmitter {

    fun submitFeedback(pluginInfoProvider: PluginInfoProvider, gitHubIssueService: GitHubIssueService, errorBean: GitHubErrorBean): SubmittedReportInfo {
        try {
            val issueTitle = errorBean.issueTitle
            var issue = gitHubIssueService.findAutoGeneratedIssueByTitle(issueTitle)
            if (issue == null) {
                issue = gitHubIssueService.submitIssue(errorBean.buildGitHubIssue())
                return createSubmittedReportInfo(issue, SubmissionStatus.NEW_ISSUE)
            } else {
                val fixedVersion = issue.getFixedVersion()
                if (fixedVersion == null) {
                    gitHubIssueService.addComment(issue.number, errorBean.issueDetailsWithoutException)
                    return createSubmittedReportInfo(issue, SubmissionStatus.DUPLICATE)
                } else if (pluginInfoProvider.runningVersionIsOlderThan(fixedVersion)) {
                    val message = ErrorReportingBundle.message("error.report.github.submission.status.fixed", fixedVersion, issue.htmlUrl, issue.number)
                    return SubmittedReportInfo(issue.htmlUrl, message, SubmissionStatus.DUPLICATE)
                } else {
                    // error was reported in past and fixed, but exists again
                    val newIssue = gitHubIssueService.submitIssue(errorBean.buildGitHubIssue())
                    gitHubIssueService.addComment(newIssue.number, "The same as in #${issue.number}.")
                    return createSubmittedReportInfo(newIssue, SubmissionStatus.NEW_ISSUE)
                }
            }
        } catch (e: Exception) {
            return SubmittedReportInfo(null, ErrorReportingBundle.message("error.report.connection.failure"), SubmissionStatus.FAILED)
        }
    }

    private fun GitHubErrorBean.buildGitHubIssue() = Issue().apply {
        title = issueTitle
        body = issueDetails
        labels = listOf(Label().apply { this.name = AUTO_GENERATED_LABEL_NAME })
    }

    private fun Issue.getFixedVersion() =
            if (body?.startsWith(FIXED_PREFIX) == true) body.lines().first().removePrefix(FIXED_PREFIX).trim()
            else null

    private fun createSubmittedReportInfo(issue: Issue, submissionStatus: SubmissionStatus): SubmittedReportInfo {
        val message = ErrorReportingBundle.message("error.report.github.submission.status.$submissionStatus", issue.htmlUrl, issue.number)
        return SubmittedReportInfo(issue.htmlUrl, message, submissionStatus)
    }

}