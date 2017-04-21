LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
#bzlib模块
bzlib_files := \
	bzip2/blocksort.c \
	bzip2/huffman.c \
	bzip2/crctable.c \
	bzip2/randtable.c \
	bzip2/compress.c \
	bzip2/decompress.c \
	bzip2/bzlib.c

LOCAL_MODULE := libbz
LOCAL_SRC_FILES := $(bzlib_files)
include $(BUILD_STATIC_LIBRARY)

#bspath模块
include $(CLEAR_VARS)
LOCAL_MODULE    := bspatch
LOCAL_SRC_FILES := bspatch/bspatch.c
LOCAL_C_INCLUDES := bspatch/bspatch.h
LOCAL_STATIC_LIBRARIES := libbz #引入libbz库
include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := patcher
LOCAL_SRC_FILES := com_example_zwy_patchupdatedemo_utils_PatchUtils.c
LOCAL_STATIC_LIBRARIES := bspatch
LOCAL_LDLIBS += -llog
LOCAL_ALLOW_UNDEFINED_SYMBOLS := true
include $(BUILD_SHARED_LIBRARY)