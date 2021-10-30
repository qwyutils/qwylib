package com.example.utilssdk;

import static com.qwy.library.data.DataUtils.RS;
import static com.qwy.library.data.DataUtils.SS;

public class SpData {
    public static void setSave(String paramString) {
        SS("Save", paramString);
    }

    public static String getSave() {
        return RS("Save");
    }
}
