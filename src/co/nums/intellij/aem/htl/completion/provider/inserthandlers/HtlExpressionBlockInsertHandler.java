package co.nums.intellij.aem.htl.completion.provider.inserthandlers;

public class HtlExpressionBlockInsertHandler extends HtlValueBlockInsertHandler {

	public static final HtlExpressionBlockInsertHandler INSTANCE = new HtlExpressionBlockInsertHandler();

	private HtlExpressionBlockInsertHandler() {
		super("=\"${}\"", 4);
	}

}
