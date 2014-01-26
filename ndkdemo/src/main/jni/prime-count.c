#include <stdlib.h>
#include <jni.h>
#include <android/log.h>

#define  LOG_TAG    "ndkdemo-native"

#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

unsigned char* createArray(int limit) {
    unsigned char* numbers = malloc(sizeof(unsigned char) * (limit + 1));
    numbers[0] = 1;
    numbers[1] = 1;
    numbers[2] = 1;
    for (int i = 3; i <= limit; i++) {
        numbers[i] = 0;
    }
    return numbers;
}

int findNextPrime(char* numbers, int limit, int p) {
    for (int i = p + 1; i <= limit; i++) {
        if (numbers[i] == 0) {
            return i;
        }
    }
    return limit;
}

int Java_net_sourcewalker_ndkdemo_calc_NativeCalculation_countPrimes( JNIEnv* env, jobject thiz, jint limit )
{
    unsigned char* numbers = createArray(limit);
    int p = 2;
    while (p < limit) {
        for (int i = 2 * p; i <= limit; i += p) {
            numbers[i] = 1;
        }
        p = findNextPrime(numbers, limit, p);
    }
    int count = 0;
    for (int i = 0; i <= limit; i++) {
        if (numbers[i] == 0) {
            count++;
        }
    }
    free(numbers);
    return count;
}
