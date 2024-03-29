/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qmuiteam.qmui.lint;


import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 检测 QMUILog 中是否使用了 F Word。
 * Created by Kayo on 2017/9/19.
 */
public class QMUIFWordDetector extends Detector
        implements Detector.UastScanner, Detector.ClassScanner {
    public static final Issue ISSUE_F_WORD =
            Issue.create("QMUIDontUseTheFWordInLog",
                    "Please, don't use the f word, type something more nicely.",
                    "Do I need to explain this? \uD83D\uDD95",
                    Category.MESSAGES, 2, Severity.WARNING,
                    new Implementation(QMUIFWordDetector.class, Scope.JAVA_FILE_SCOPE));

//    @Override
//    public List<String> getApplicableMethodNames() {
//        return Arrays.asList("e", "w", "i", "d");
//    }


    @Override
    public void visitMethod(@NotNull JavaContext context,
                            @Nullable JavaElementVisitor visitor,
                            @NotNull PsiMethodCallExpression call,
                            @NotNull PsiMethod method) {
        super.visitMethod(context, visitor, call, method);
        String fullyQualifiedMethodName = call.getMethodExpression().getQualifiedName();
        context.report(
                ISSUE_F_WORD, method,
                context.getLocation(method), fullyQualifiedMethodName);
        if (fullyQualifiedMethodName.startsWith("android.util.Log") ||
                fullyQualifiedMethodName.startsWith("com.qmuiteam.qmui.QMUILog")) {
            PsiExpressionList args = call.getArgumentList();
            if (args.isEmpty()) {
                return;
            }
            for (PsiExpression expression : args.getExpressions()) {
                String input = expression.toString();
                if (input != null && input.contains("fuck")) {
                    context.report(
                            ISSUE_F_WORD, expression,
                            context.getLocation(expression), "\uD83D\uDD95");
                }
            }
        }
    }
}
