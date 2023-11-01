package com.luan.getlib;

import com.luan.getlib.utils.Database;
import com.luan.getlib.utils.InputReader;
import com.luan.getlib.views.InitialView;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */

public class Getlib {
    public static void main(String[] args) {
        InitialView.showInitialMenu();
        closeResources();
    }

    private static void closeResources() {
        InputReader.closeScanner();
        Database.closeSectionFactory();
    }
}