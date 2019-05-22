package padl.creator.cppfile.eclipse.test.big;

import java.util.ArrayList;
import java.util.Iterator;

import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IWalker;
import util.lang.Modifier;

public class JNIDraftVisitor implements IWalker {
	private final ArrayList<String> listOfNativeMethods = new ArrayList<String>();
	private final ArrayList<String> list = new ArrayList<String>();
	private IClass currentclass;
	private IPackage currentpackage;
	private IPackageDefault currentdefaultpackage;
	private IMethod currentmethod;

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return this.listOfNativeMethods;
	}

	@Override
	public void open(IPackage aPackage) {
		// TODO Auto-generated method stub
		this.currentpackage = aPackage;
	}

	@Override
	public void open(IPackageDefault aPackage) {
		// TODO Auto-generated method stub
		this.currentdefaultpackage = aPackage;
	}

	@Override
	public void open(IClass aClass) {
		// TODO Auto-generated method stub
		this.currentclass = aClass;
		//list.add(currentclass.getDisplayName());
	}

	@Override
	public void open(IMethod aMethod) {
		// TODO Auto-generated method stub
		this.currentmethod=aMethod;
		if (Modifier.isNative(aMethod.getVisibility())) {
			this.listOfNativeMethods.add(aMethod.getDisplayName());
		}
	}

	// ********************** NOT USED YET ***********************

	@Override
	public void close(IAbstractModel anAbstractModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IClass aClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IConstructor aConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IDelegatingMethod aDelegatingMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IGetter aGetter) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("GETTER  " + aGetter.getDisplayName());

	}

	@Override
	public void close(IGhost aGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IInterface anInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMemberClass aMemberClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMemberGhost aMemberGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMemberInterface aMemberInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMethod aMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IPackage aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IPackageDefault aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(ISetter aSetter) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("SETTER  " + aSetter.getDisplayName());

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open(IAbstractModel anAbstractModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IConstructor aConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IDelegatingMethod aDelegatingMethod) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("DELEGATING METHOD  ");

	}

	@Override
	public void open(IGetter aGetter) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("GETTER  ");

	}

	@Override
	public void open(IGhost aGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IInterface anInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IMemberClass aMemberClass) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("MEMBERCLASS  ");

	}

	@Override
	public void open(IMemberGhost aMemberGhost) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("MEMBERGHOST  ");

	}

	@Override
	public void open(IMemberInterface aMemberInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(ISetter aSetter) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("SETTER  ");

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("RESET  ");

	}

	@Override
	public void unknownConstituentHandler(String aCalledMethodName, IConstituent aConstituent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IAggregation anAggregation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IAssociation anAssociation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IComposition aComposition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IContainerAggregation aContainerAggregation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IContainerComposition aContainerComposition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ICreation aCreation) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("CREATION  ");

	}

	@Override
	public void visit(IField aField) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("FIELD  " + aField.getDisplayName());
		
	}

	@Override
	public void visit(IMethodInvocation aMethodInvocation) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("INVOC  " + aMethodInvocation.getCalledMethod());

	}

	@Override
	public void visit(IParameter aParameter) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println(currentmethod.getDisplayName() + ": " + aParameter.getDisplayName());

	}

	@Override
	public void visit(IPrimitiveEntity aPrimitiveEntity) {
		// TODO Auto-generated method stub
		// TODO Remove next line
		System.out.println("PRIM ENT  ");

	}

	@Override
	public void visit(IUseRelationship aUse) {
		// TODO Auto-generated method stub
		
	}
}
