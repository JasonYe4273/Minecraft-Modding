package com.JasonILTG.ScienceMod.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnnotationHelper
{
	public static ArrayList<Field> getFieldsWithAnnotation(Object obj, Class<? extends Annotation> annotationClass)
	{
		ArrayList<Field> fieldsWithAnnotation = new ArrayList<Field>();
		
		for (Field f : obj.getClass().getFields())
		{
			if (f.getAnnotation(annotationClass) != null) {
				fieldsWithAnnotation.add(f);
			}
		}
		
		return fieldsWithAnnotation;
	}
}
