package co.nums.intellij.aem.htl.completion.provider;

class HtlSimpleBlockInsertHandler extends HtlValueBlockInsertHandler {

	public static final HtlSimpleBlockInsertHandler INSTANCE = new HtlSimpleBlockInsertHandler();

	@Override
	protected String getInsertionString() {
		return "=\"\"";
	}

	@Override
	protected int getInsertionOffset() {
		return 2;
	}

}
