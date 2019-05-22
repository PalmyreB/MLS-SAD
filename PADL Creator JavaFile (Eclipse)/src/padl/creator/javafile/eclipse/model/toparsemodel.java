package padl.creator.javafile.eclipse.model;

import java.io.File;
import java.util.Iterator;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IUseRelationship;
import util.lang.Modifier;
	
public class toparsemodel {

	public toparsemodel() {
		// TODO Auto-generated constructor stub
	}
		
	private static String parseModifier(final int modifier) {
			 
	        if (modifier == Modifier.PRIVATE)
	            return "private";

	        else if (modifier == Modifier.PROTECTED) {
	            return "protected ";
	        }

	        else if (modifier == Modifier.PUBLIC) {
	            return "public";
	        }

	        else if (modifier == Modifier.ABSTRACT) {
	            return "abstract";
	        }

	        else if (modifier == Modifier.STATIC) {
	            return "static";
	        }

	        else if (modifier == Modifier.FINAL) {
	            return "final";
	        }

	        else {
	            return "other modifier";
	        }
	}

	static void iterateAbstractModel(IAbstractLevelModel aModel,
				String name) {
			Iterator<IFirstClassEntity> iterOnTopEntities;
			iterOnTopEntities = aModel.getIteratorOnTopLevelEntities();
			while (iterOnTopEntities.hasNext()) {
				IFirstClassEntity entity = iterOnTopEntities.next();
				System.out
						.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
				System.out
						.println("Entity model " + name + ":"
								+ entity.getDisplayID() + " "
								+ entity.getClass().getName());

				if (entity instanceof IClass) {

					try {
						System.out.println("Number of methods:"
								+ ((IClass) entity).getNumberOfConstituents(Class
										.forName("padl.kernel.IMethod")));
						Iterator<IFirstClassEntity> iter = entity
								.getIteratorOnInheritedEntities();
						// is class abstract
						System.out.println("isAbstract: " + entity.isAbstract());

						while (iter.hasNext()) {

							IFirstClassEntity superEntity = iter.next();
							System.out.println("superclass "
									+ superEntity.getDisplayID());

						}

					} catch (ClassNotFoundException e1) {

						e1.printStackTrace();
					}

				}

				Iterator<IConstituent> iterOnEntityConstituents = entity
						.getIteratorOnConstituents();
				while (iterOnEntityConstituents.hasNext()) {
					IConstituent elt = iterOnEntityConstituents.next();
					System.out.println("=====Constituent:" + elt.getDisplayID()
							+ " " + elt.getClass().getName());
					
					if (elt instanceof IMethod) {
						System.out.println("========Method return type:"
								+ ((IMethod) elt).getDisplayReturnType());

						try {
							System.out.println("========Number of parameters:"
									+ ((IMethod) elt).getNumberOfConstituents(Class
											.forName("padl.kernel.IParameter")));
						} catch (ClassNotFoundException e) {

							e.printStackTrace();
						}
						
						/*if (((IMethod) elt).getCodeLines()!=null){
							System.out.print("\n========LOC:");
							
							for (String loc: ((IMethod) elt).getCodeLines())
									System.out.print(loc);
							
						}*/

						/*
						 * For iterating the method invocation elements of the
						 * method
						 */

						System.out.println("==========Method Invocation========");
						Iterator<IConstituent> iterOnConstituents;
						iterOnConstituents = ((IMethod) elt)
								.getIteratorOnConstituents(IMethodInvocation.class);// getIteratorOnConstituents(Class.forName("padl.kernel.IParameter"));

						String idMethodInvocation = null;
						while (iterOnConstituents.hasNext()) {
							// IConstituent el=iterOnConstituents.next();
							IMethodInvocation iMI = (IMethodInvocation) iterOnConstituents
									.next();	
							
							if (iMI.getFirstCallingField() != null){
							System.out.println("iMI.getFirstCallingField() "+
							iMI.getFirstCallingField().getDisplayID());
							}
							
							System.out.println("iMI.getDisplayID():"
									+ iMI.getDisplayID());
							// System.out.println("par.getDisplayTypeName():"+par.getDisplayTypeName());
							System.out.println("iMI.getDisplayName():"
									+ iMI.getDisplayName());

							if (iMI.getCalledMethod() != null)
								System.out.println("iMI.getCalledMethod():"
										+ iMI.getCalledMethod().getDisplayName());

							System.out.print("Cardinality:" + iMI.getCardinality()
									+ " ");

							System.out.print("Visibility:"
									+ parseModifier(iMI.getVisibility()) + " ");

							System.out.print("Type:" + iMI.getType() + " ");

							if (iMI.getTargetEntity() != null)
								System.out
										.println("iMI.getTargetEntity().getDisplayID():"
												+ iMI.getTargetEntity()
														.getDisplayID());

							if (iMI.getFieldDeclaringEntity() != null)
								System.out.println("iMI.getFieldDeclaringEntity, i.e. entity declaring the field:"
										+ iMI.getFieldDeclaringEntity()
												.getDisplayID());
							/*int x = Modifier.PRIVATE;

							System.out
									.println("*****************************************");
	*/
						}
						
						System.out.println("==========END Method Invocation========");

					}

					else {

						if (elt instanceof IField) {
							// System.out.println("========Field return type:"+((IField)elt).getDisplayPath());//gives
							// nothing
							System.out.println("Field: get type:"
									+ ((IField) elt).getDisplayTypeName()+ " FieldName: "+
									((IField) elt).getDisplayID());						
						}

						else {
							if (elt instanceof IUseRelationship) {
								System.out.println("UseRelationship DisplayName():"
										+ elt.getDisplayName());
								if (elt instanceof IAssociation) {

									System.out.println("getDisplayID() "
											+ elt.getDisplayID());

									if (((IAssociation) elt).getTargetEntity()
											.getDisplayID() != null)

										System.out.println("getTargetEntity():"
												+ ((IAssociation) elt)
														.getTargetEntity()
														.getDisplayID());
								}

							}

							else {

								System.out.println("elt.getDisplayName():"
										+ elt.getDisplayName());
							}

						}

					}

				}
			}
		}
	}
