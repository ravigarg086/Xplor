package com.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseUtils extends Initialize_Browser {

	public BaseUtils(WebDriver driver) {
		this.driver = driver;
	}

	// Initialize_Browser browser;
	public void waitForElement(WebDriver webDriver, String locator,
			long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(webDriver, timeoutInSeconds);
		System.out.println("xpath==" + locator);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(locator)));
	}

	public void wait_new() {
		boolean breakIt = true;
		while (true) {
			breakIt = true;
			try {
				// write your code here
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}
			if (breakIt) {
				break;
			}

		}
	}

	// Select value by visible text in drop down
	public void select_DropdownList(WebElement locator, String visibleText) {

		boolean breakIt = true;
		while (breakIt) {
			boolean state = false;
			try {
				while (!state) {
					Thread.sleep(1000);
					state = locator.isDisplayed();

				}
				Select signVerf = new Select(locator);
				Thread.sleep(1000);
				if (!visibleText.equalsIgnoreCase("")) {
					signVerf.selectByValue(visibleText);
				}
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}

			if (breakIt) {
				break;
			}
		}
	}

	public void enter_Text(WebElement locator, String visibleText) {
		boolean breakIt = true;
		while (breakIt) {
			boolean state = false;
			try {
				while (!state) {
					Thread.sleep(1000);
					state = locator.isDisplayed();

				}

				Thread.sleep(1000);
				if (!visibleText.equalsIgnoreCase("")) {
					locator.clear();
					locator.sendKeys(visibleText, Keys.TAB);
				}
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}

			if (breakIt) {
				break;
			}
		}
	}

	public void selectvalueByText_DropdownList(WebElement locator,
			String visibleText) {
		boolean breakIt = true;
		while (breakIt) {
			boolean state = false;
			try {
				while (!state) {
					Thread.sleep(1000);
					state = locator.isDisplayed();

				}
				Select signVerf = new Select(locator);
				Thread.sleep(1000);
				if (!visibleText.equalsIgnoreCase("")) {
					signVerf.selectByVisibleText(visibleText);
				}
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}

			if (breakIt) {
				break;
			}
		}
	}

	// check WebElement Exist or not

	public boolean isWebElementExist(WebElement value) {
		try {

			value.getTagName();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void click_On_Button(WebElement locator) {
		boolean breakIt = true;
		while (breakIt) {
			boolean state = false;
			try {
				while (!state) {
					Thread.sleep(1000);
					state = locator.isDisplayed();

				}

				Thread.sleep(1000);
				locator.click();
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}

			if (breakIt) {
				break;
			}
		}
	}

	public void click_On_Link(WebElement locator) throws Exception {
		boolean breakIt = true;
		while (breakIt) {
			boolean state = false;
			try {
				while (!state) {
					Thread.sleep(1000);
					state = locator.isDisplayed();

				}

				Thread.sleep(1000);
				locator.click();
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}

			if (breakIt) {
				break;
			}
		}
	}

	// Select value in dropdownlist
	public void dropdown_selectByValue(WebElement element, String value) {

		boolean breakIt = true;

		while (true) {
			breakIt = true;
			try {
				Thread.sleep(5000);
				Select signVerf = new Select(element);
				signVerf.selectByValue(value);

			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}

			if (breakIt) {
				break;
			}
		}
	}

	// Select value by visible text in drop down
	public void dropdown_selectByVisibleText(WebElement drpDwnWebElement,
			String visibleText) {
		boolean breakIt = true;

		while (true) {
			breakIt = true;
			try {
				Select signVerf = new Select(drpDwnWebElement);
				signVerf.selectByVisibleText(visibleText);
			} catch (Exception e) {
				if (e.getMessage().contains("element is not attached")) {
					breakIt = false;
				}
			}

			if (breakIt) {
				break;
			}
		}
	}

}
