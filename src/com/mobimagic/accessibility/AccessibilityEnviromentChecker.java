/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobimagic.accessibility;

import java.util.List;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * 
 * Helper class to check accessibility service status.
 *
 */
public class AccessibilityEnviromentChecker {
    /**
     * No accessibility service is enabled.
     */
    public static final int NO_SERVICE_RUNNING = 0;
    /**
     * Some untrusted accessibility service (client) is running.
     * In this case, we should never provide private information such as 
     * accounts and passwords within {@link AccessibilityEvent} or {@link AccessibilityNodeInfo}.
     */
    public static final int UNTRUSTED_SERVICE_RUNNING = 1;
    /**
     * Accessibility is enabled with all clients/services trusted.
     */
    public static final int TRUSTED_SERVICE_RUNNING = 2;
   
    /**
     * Check current accessibility environment.
     * @param context
     * @return the status
     */
    public static final int check(Context context) {
        AccessibilityManager am = (AccessibilityManager)context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> clients = am.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        if (clients == null || clients.isEmpty()) {
            return NO_SERVICE_RUNNING;
        }
        for (AccessibilityServiceInfo client : clients) {
           if (!isTrustedClient(client)) {
               return UNTRUSTED_SERVICE_RUNNING;
           }
        }
        return TRUSTED_SERVICE_RUNNING;
    }
    /**
     * Implement your own policy to decide if a client is trusted.
     * @param client the accessibility service.
     * @return
     */
    private static final boolean isTrustedClient(AccessibilityServiceInfo client) {
        return true;
    }
}
