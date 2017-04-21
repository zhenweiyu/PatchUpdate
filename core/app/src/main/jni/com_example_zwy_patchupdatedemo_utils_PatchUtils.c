
#include <jni.h>
#include <stdlib.h>
#include <android/log.h>
#include "com_example_zwy_patchupdatedemo_utils_PatchUtils.h"
#include "bspatch/bspatch.h"

JNIEXPORT jint JNICALL Java_com_example_zwy_patchupdatedemo_utils_PatchUtils_patch
        (JNIEnv *env, jclass jclz, jstring oldPath, jstring newPath , jstring patchPath){
    char * ch[4];
    ch[0] = "bspatch";
    ch[1] = (char*) ((*env)->GetStringUTFChars(env, oldPath, 0));
    ch[2] = (char*) ((*env)->GetStringUTFChars(env, newPath, 0));
    ch[3] = (char*) ((*env)->GetStringUTFChars(env, patchPath, 0));

    int ret = applypatch(4, ch);
    (*env)->ReleaseStringUTFChars(env, oldPath, ch[1]);
    (*env)->ReleaseStringUTFChars(env, newPath, ch[2]);
    (*env)->ReleaseStringUTFChars(env, patchPath, ch[3]);

    //return (*env)->NewStringUTF(env,"success");
    return ret;
};