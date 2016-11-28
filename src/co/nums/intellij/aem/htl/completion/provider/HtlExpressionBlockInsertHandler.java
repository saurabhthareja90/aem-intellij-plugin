package co.nums.intellij.aem.htl.completion.provider;

class HtlExpressionBlockInsertHandler extends HtlValueBlockInsertHandler {

	public static final HtlExpressionBlockInsertHandler INSTANCE = new HtlExpressionBlockInsertHandler();

	@Override
	protected String getInsertionString() {
		return "=\"${}\"";
	}

	@Override
	protected int getInsertionOffset() {
		return 4;
	}

}
