package com.book.manager.dialect;

public class StringArrayTypeDescriptor
		extends AbstractArrayTypeDescriptor<String[]> {
	public static final StringArrayTypeDescriptor INSTANCE = new StringArrayTypeDescriptor();
	public StringArrayTypeDescriptor() {
		super( String[].class );
	}
	@Override
	protected String getSqlArrayType() {
		return "text";
	}
}
