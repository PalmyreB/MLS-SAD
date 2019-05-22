/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_ogre4j_Node */

#ifndef _Included_org_ogre4j_Node
#define _Included_org_ogre4j_Node
#ifdef __cplusplus
extern "C" {
#endif
/* Inaccessible static: table */
#undef org_ogre4j_Node_TS_LOCAL
#define org_ogre4j_Node_TS_LOCAL 0L
#undef org_ogre4j_Node_TS_PARENT
#define org_ogre4j_Node_TS_PARENT 1L
#undef org_ogre4j_Node_TS_WORLD
#define org_ogre4j_Node_TS_WORLD 2L
/*
 * Class:     org_ogre4j_Node
 * Method:    _getDerivedOrientation
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node__1getDerivedOrientation
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    _getDerivedPosition
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node__1getDerivedPosition
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    _getDerivedScale
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node__1getDerivedScale
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    addChild
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_addChild
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    cancelUpdate
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_cancelUpdate
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    createChild
 * Signature: (IFFFFFFF)I
 */
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_createChild__IFFFFFFF
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat, jfloat, jfloat, jfloat, jfloat);

/*
 * Class:     org_ogre4j_Node
 * Method:    createChild
 * Signature: (ILjava/lang/String;FFFFFFF)I
 */
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_createChild__ILjava_lang_String_2FFFFFFF
  (JNIEnv *, jclass, jint, jstring, jfloat, jfloat, jfloat, jfloat, jfloat, jfloat, jfloat);

/*
 * Class:     org_ogre4j_Node
 * Method:    getChild
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_getChild__II
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getChild
 * Signature: (ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_getChild__ILjava_lang_String_2
  (JNIEnv *, jclass, jint, jstring);

/*
 * Class:     org_ogre4j_Node
 * Method:    getInheritScale
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_ogre4j_Node_getInheritScale
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getInitialOrientation
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getInitialOrientation
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getInitialPosition
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getInitialPosition
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getInitialScale
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getInitialScale
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_ogre4j_Node_getName
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getOrientation
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getOrientation
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getParent
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_getParent
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getPosition
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getPosition
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getScale
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getScale
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    getWorldPosition
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_org_ogre4j_Node_getWorldPosition
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    needUpdate
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_needUpdate
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    numChildren
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_numChildren
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    pitch
 * Signature: (IFI)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_pitch
  (JNIEnv *, jclass, jint, jfloat, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    removeAllChildren
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_removeAllChildren
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    removeChild
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_removeChild__II
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    removeChild
 * Signature: (IS)I
 */
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_removeChild__IS
  (JNIEnv *, jclass, jint, jshort);

/*
 * Class:     org_ogre4j_Node
 * Method:    removeChild
 * Signature: (ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_ogre4j_Node_removeChild__ILjava_lang_String_2
  (JNIEnv *, jclass, jint, jstring);

/*
 * Class:     org_ogre4j_Node
 * Method:    requestUpdate
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_requestUpdate
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    resetOrientation
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_resetOrientation
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    resetToInitialState
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_resetToInitialState
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    roll
 * Signature: (IFI)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_roll
  (JNIEnv *, jclass, jint, jfloat, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    rotateByQuaternion
 * Signature: (IFFFF)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_rotateByQuaternion
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat, jfloat);

/*
 * Class:     org_ogre4j_Node
 * Method:    rotateQuat
 * Signature: (IFFFFI)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_rotateQuat
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat, jfloat, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    rotateVec
 * Signature: (IFFFFI)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_rotateVec
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat, jfloat, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    scale
 * Signature: (IFFF)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_scale
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat);

/*
 * Class:     org_ogre4j_Node
 * Method:    setCustomParameter
 * Signature: (IILorg/ogre4j/Vector4;)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setCustomParameter
  (JNIEnv *, jclass, jint, jint, jobject);

/*
 * Class:     org_ogre4j_Node
 * Method:    setInheritScale
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setInheritScale
  (JNIEnv *, jclass, jint, jboolean);

/*
 * Class:     org_ogre4j_Node
 * Method:    setInitialState
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setInitialState
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    setOrientation
 * Signature: (IFFFF)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setOrientation
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat, jfloat);

/*
 * Class:     org_ogre4j_Node
 * Method:    setPosition
 * Signature: (IFFF)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setPosition
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat);

/*
 * Class:     org_ogre4j_Node
 * Method:    setRenderDetailOverrideable
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setRenderDetailOverrideable
  (JNIEnv *, jclass, jint, jboolean);

/*
 * Class:     org_ogre4j_Node
 * Method:    setScale
 * Signature: (IFFF)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_setScale
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat);

/*
 * Class:     org_ogre4j_Node
 * Method:    translate
 * Signature: (IFFFI)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_translate
  (JNIEnv *, jclass, jint, jfloat, jfloat, jfloat, jint);

/*
 * Class:     org_ogre4j_Node
 * Method:    yaw
 * Signature: (IFI)V
 */
JNIEXPORT void JNICALL Java_org_ogre4j_Node_yaw
  (JNIEnv *, jclass, jint, jfloat, jint);

#ifdef __cplusplus
}
#endif
#endif
