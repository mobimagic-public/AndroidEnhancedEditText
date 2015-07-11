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

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
/**
 * An enhanced text input widget to keep passwords secure when accessibility enabled on your device.
 * {@link android.wiget.EditText} in Android, the most widely used, would send password to accessibility services
 * after its input type is switched to 'normal mode'.
 */
public class EnhancedEditText extends EditText {

    public EnhancedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Clear all private info from accessibility event.
     * @param event
     */
    private final void clearAccessibilityInfo(AccessibilityEvent event) {
        event.setBeforeText("");
        event.setContentDescription("");
        event.setFromIndex(0);
        event.setToIndex(0);
        event.setItemCount(0);
    }
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (AccessibilityEnviromentChecker.check(getContext()) == AccessibilityEnviromentChecker.UNTRUSTED_SERVICE_RUNNING) {
            clearAccessibilityInfo(event);  
        }else {
            super.onPopulateAccessibilityEvent(event);
        }
         
    }
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (AccessibilityEnviromentChecker.check(getContext()) == AccessibilityEnviromentChecker.UNTRUSTED_SERVICE_RUNNING) {
            clearAccessibilityInfo(event);  
            return false;
        }else {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
    }
    /**
     * Never populate private info to AccessiblityServices.
     */
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info)  {
        if (AccessibilityEnviromentChecker.check(getContext()) != AccessibilityEnviromentChecker.UNTRUSTED_SERVICE_RUNNING) {
            super.onInitializeAccessibilityNodeInfo(info);
        }
    }
}
