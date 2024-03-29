package com.orrish.automation.appiumselenium;

import com.orrish.automation.utility.report.ReportUtility;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static com.orrish.automation.entrypoint.GeneralSteps.getMethodStyleStepName;

public class SeleniumAppiumActions {

    protected boolean isWebStepPassed = true;
    protected boolean isMobileStepPassed = true;
    protected SeleniumPageMethods seleniumPageMethods = SeleniumPageMethods.getInstance();
    protected AppiumPageMethods appiumPageMethods = AppiumPageMethods.getInstance();

    public boolean executeOnWebAndReturnBoolean(Object... args) {
        Object valueToReturn = executeOnWebAndReturnObject(args);
        return valueToReturn != null && Boolean.parseBoolean(valueToReturn.toString());
    }

    public String executeOnWebAndReturnString(Object... args) {
        Object valueToReturn = executeOnWebAndReturnObject(args);
        if (valueToReturn == null)
            return "";
        ReportUtility.reportInfo(getMethodStyleStepName(args) + " returned " + valueToReturn);
        return valueToReturn.toString();
    }

    public Map executeOnWebAndReturnMap(Object... args) {
        Object valueToReturn = executeOnWebAndReturnObject(args);
        if (valueToReturn == null)
            return null;
        ReportUtility.reportInfo(getMethodStyleStepName(args) + " returned " + valueToReturn);
        return (Map) valueToReturn;
    }

    public List executeOnWebAndReturnList(Object... args) {
        Object valueToReturn = executeOnWebAndReturnObject(args);
        if (valueToReturn == null)
            return null;
        ReportUtility.reportInfo(getMethodStyleStepName(args) + " returned " + valueToReturn);
        return (List) valueToReturn;
    }

    public boolean executeOnMobileAndReturnBoolean(Object... args) {
        Object valueToReturn = executeOnMobileAndReturnObject(args);
        return valueToReturn != null && Boolean.parseBoolean(valueToReturn.toString());
    }

    public String executeOnMobileAndReturnString(Object... args) {
        Object valueToReturn = executeOnMobileAndReturnObject(args);
        if (valueToReturn == null)
            return "";
        ReportUtility.reportInfo(getMethodStyleStepName(args) + " returned " + valueToReturn);
        return valueToReturn.toString();
    }

    public Map executeOnMobileAndReturnMap(Object... args) {
        Object valueToReturn = executeOnMobileAndReturnObject(args);
        if (valueToReturn == null)
            return null;
        ReportUtility.reportInfo(getMethodStyleStepName(args) + " returned " + valueToReturn);
        return (Map) valueToReturn;
    }

    public List executeOnMobileAndReturnList(Object... args) {
        Object valueToReturn = executeOnMobileAndReturnObject(args);
        if (valueToReturn == null)
            return null;
        ReportUtility.reportInfo(getMethodStyleStepName(args) + " returned " + valueToReturn);
        return (List) valueToReturn;
    }

    protected Object executeOnMobileAndReturnObject(Object... args) {
        if (!isMobileStepPassed) {
            ReportUtility.reportInfo(getMethodStyleStepName(args) + " ignored due to last failure.");
            return null;
        }
        try {
            switch (args[0].toString()) {
                case "launchAppOnDevice":
                    isMobileStepPassed = appiumPageMethods.launchAppOnDevice();
                    break;
                case "takeMobileScreenshotWithText":
                    isMobileStepPassed = appiumPageMethods.takeMobileScreenshotWithText(args[1].toString());
                    break;
                case "quitAppOnDevice":
                    isMobileStepPassed = appiumPageMethods.quitAppOnDevice();
                    break;
                case "inMobileGoBackToPreviousPage":
                    isMobileStepPassed = appiumPageMethods.goBackToPreviousPage();
                    break;
                case "pressHomeKey":
                    isMobileStepPassed = appiumPageMethods.pressHomeKey();
                    break;
                case "pressBackKey":
                    isMobileStepPassed = appiumPageMethods.pressBackKey();
                    break;
                case "swipeOnceVertically":
                    isMobileStepPassed = appiumPageMethods.swipeOnceVertically();
                    break;
                case "tapFor":
                    isMobileStepPassed = appiumPageMethods.tapFor(args[1].toString());
                    break;
                case "tapWithText":
                    isMobileStepPassed = appiumPageMethods.tapWithText(args[1].toString(), args[2].toString());
                    break;
                case "tapWhicheverIsDisplayedIn":
                    isMobileStepPassed = appiumPageMethods.tapWhicheverIsDisplayedIn(args[1].toString());
                    break;
                case "inMobileWaitUntilIsGoneFor":
                    isMobileStepPassed = appiumPageMethods.waitUntilIsGoneFor(args[1].toString());
                    break;
                case "inMobileWaitUntilIsDisplayedFor":
                    isMobileStepPassed = appiumPageMethods.waitUntilIsDisplayedFor(args[1].toString());
                    break;
                case "inMobileWaitUntilOneOfTheLocatorsIsEnabled":
                    isMobileStepPassed = appiumPageMethods.waitUntilOneOfTheLocatorsIsEnabled(args[1].toString());
                    break;
                case "inMobileWaitUntilElementTextContains":
                    isMobileStepPassed = appiumPageMethods.waitUntilElementTextContains(args[1].toString(), args[2].toString());
                    break;
                case "inMobileWaitUntilElementTextDoesNotContain":
                    isMobileStepPassed = appiumPageMethods.waitUntilElementTextDoesNotContain(args[1].toString(), args[2].toString());
                    break;
                case "inMobileEnterInTextFieldFor":
                    isMobileStepPassed = appiumPageMethods.enterInTextFieldFor(args[1].toString(), args[2].toString());
                    break;
                case "inMobileGetTextFromLocator":
                    return appiumPageMethods.getTextFromLocator(args[1].toString());
                default:
                    throw new Exception(args[0].toString() + " method not implemented yet.");
            }
        } catch (Exception ex) {
            isMobileStepPassed = false;
            appiumPageMethods.reportException(args, ex);
            return null;
        }
        appiumPageMethods.reportExecutionStatus(isMobileStepPassed, args);
        return isMobileStepPassed;
    }

    protected Object executeOnWebAndReturnObject(Object... args) {
        if (!isWebStepPassed) {
            ReportUtility.reportInfo(getMethodStyleStepName(args) + " ignored due to last failure.");
            return null;
        }
        try {
            /*
            //Whole of below switch-case can be done using reflection, but commented now because it is slower.
            String methodName = args[0].toString();
            if (methodName.startsWith("inBrowser")) {
                methodName = methodName.replaceFirst("inBrowser", "");
                methodName = String.valueOf(methodName.charAt(0)).toLowerCase() + methodName.substring(1);
            }
            if (args.length == 1) {
                Method method = pageMethods.getClass().getMethod(methodName);
                if (!method.getReturnType().getTypeName().contains("boolean"))
                    return method.invoke(pageMethods);
                isWebStepPassed = (boolean) method.invoke(pageMethods);
            } else if (args.length == 2) {
                Method method = pageMethods.getClass().getMethod(methodName, String.class);
                if (!method.getReturnType().getTypeName().contains("boolean"))
                    return method.invoke(pageMethods, args[1].toString());
                isWebStepPassed = (boolean) method.invoke(pageMethods, args[1].toString());
            } else if (args.length == 3) {
                Method method = pageMethods.getClass().getMethod(methodName, String.class, String.class);
                if (!method.getReturnType().getTypeName().contains("boolean"))
                    return method.invoke(pageMethods, args[1].toString(), args[2].toString());
                isWebStepPassed = (boolean) method.invoke(pageMethods, args[1].toString(), args[2].toString());
            } else {
                isWebStepPassed = false;
                throw new Exception("Method " + GeneralSteps.getMethodStyleStepName(args) + " not implemented.");
            }
            //*/
            ///*
            switch (args[0].toString()) {
                case "launchBrowserAndNavigateTo":
                    isWebStepPassed = seleniumPageMethods.launchBrowserAndNavigateTo(args[1].toString());
                    break;
                case "inBrowserNavigateTo":
                    isWebStepPassed = seleniumPageMethods.navigateTo(args[1].toString());
                    break;
                case "inBrowserNavigateBack":
                    isWebStepPassed = seleniumPageMethods.navigateBack();
                    break;
                case "closeBrowser":
                    isWebStepPassed = seleniumPageMethods.closeBrowser();
                    break;
                case "quitBrowser":
                    isWebStepPassed = seleniumPageMethods.quitBrowser();
                    break;
                case "refreshWebPage":
                    isWebStepPassed = seleniumPageMethods.refreshWebPage();
                    break;
                case "maximizeTheWindow":
                    isWebStepPassed = seleniumPageMethods.maximizeTheWindow();
                    break;
                case "takeWebScreenshotWithText":
                    return seleniumPageMethods.takeWebScreenshotWithText(args[1].toString());
                case "clickFor":
                    isWebStepPassed = seleniumPageMethods.click(args[1].toString());
                    break;
                case "clickWithText":
                    isWebStepPassed = seleniumPageMethods.clickWithText(args[1].toString(), args[2].toString());
                    break;
                case "clickWhicheverIsDisplayedIn":
                    isWebStepPassed = seleniumPageMethods.clickWhicheverIsDisplayedIn(args[1].toString());
                    break;
                case "clickRowContainingText":
                    return seleniumPageMethods.clickRowContainingText(args[1].toString());
                case "selectCheckboxForText":
                    isWebStepPassed = seleniumPageMethods.selectCheckboxForText(args[1].toString());
                    break;
                case "unselectCheckboxForText":
                    isWebStepPassed = seleniumPageMethods.unselectCheckboxForText(args[1].toString());
                    break;
                case "waitUntilIsGoneFor":
                    isWebStepPassed = seleniumPageMethods.waitUntilIsGone(args[1].toString());
                    break;
                case "waitUntilIsDisplayedFor":
                    isWebStepPassed = seleniumPageMethods.waitUntilIsDisplayed(args[1].toString());
                    break;
                case "waitUntilOneOfTheLocatorsIsDisplayed":
                    isWebStepPassed = (seleniumPageMethods.waitUntilOneOfTheLocatorsIsDisplayed(args[1].toString()) != null);
                    break;
                case "waitUntilOneOfTheLocatorsIsEnabled":
                    isWebStepPassed = (seleniumPageMethods.waitUntilOneOfTheLocatorsIsEnabled(args[1].toString()) != null);
                    break;
                case "waitUntilElementTextContains":
                    isWebStepPassed = seleniumPageMethods.waitUntilElementTextContains(args[1].toString(), args[2].toString());
                    break;
                case "waitUntilElementTextDoesNotContain":
                    isWebStepPassed = seleniumPageMethods.waitUntilElementTextDoesNotContain(args[1].toString(), args[2].toString());
                    break;
                case "enterInTextFieldFor":
                    isWebStepPassed = seleniumPageMethods.enterInTextField(args[1].toString(), args[2].toString());
                    break;
                case "enterInTextFieldNumber":
                    isWebStepPassed = seleniumPageMethods.enterInTextFieldNumber(args[1].toString(), args[2].toString());
                    break;
                case "isTextPresentInWebpage":
                    isWebStepPassed = seleniumPageMethods.isTextPresentInWebpage(args[1].toString());
                    break;
                case "getAlertText":
                    return seleniumPageMethods.getAlertText();
                case "dismissAlertIfPresent":
                    isWebStepPassed = seleniumPageMethods.dismissAlertIfPresent();
                    break;
                case "acceptAlertIfPresent":
                    isWebStepPassed = seleniumPageMethods.acceptAlertIfPresent();
                    break;
                case "selectFromDropdown":
                    isWebStepPassed = seleniumPageMethods.selectFromDropdown(args[1].toString(), args[2].toString());
                    break;
                case "selectDropdownByText":
                    isWebStepPassed = seleniumPageMethods.selectDropdownByText(args[1].toString());
                    break;
                case "getTextFromLocator":
                    return seleniumPageMethods.getTextFromLocator(args[1].toString());
                case "executeJavascript":
                    isWebStepPassed = seleniumPageMethods.executeJavascript(args[1].toString());
                    break;
                case "scrollTo":
                    isWebStepPassed = seleniumPageMethods.scrollTo(args[1].toString());
                    break;
                case "scrollToBottom":
                    isWebStepPassed = seleniumPageMethods.scrollToBottom();
                    break;
                case "getCurrentWindowId":
                    return seleniumPageMethods.getCurrentWindowId();
                case "switchToWindowId":
                    isWebStepPassed = seleniumPageMethods.switchToWindowId(args[1].toString());
                    break;
                default:
                    throw new Exception(args[0].toString() + " method not implemented yet.");
            }
            //*/
        } catch (Exception ex) {
            isWebStepPassed = false;
            seleniumPageMethods.reportExecutionStatus(false, args);
            Exception exception = (ex instanceof InvocationTargetException) ? (Exception) ((InvocationTargetException) ex).getTargetException() : ex;
            seleniumPageMethods.reportException(args, exception);
            return null;
        }
        seleniumPageMethods.reportExecutionStatus(isWebStepPassed, args);
        return isWebStepPassed;
    }

}
