package se.sundsvall.smssender.api.util;

import java.util.Optional;
import se.sundsvall.smssender.api.model.Sender;

public final class RequestCleaner {

	private RequestCleaner() {
		// Intentionally empty
	}

	public static String cleanMessage(final String message) {
		return Optional.ofNullable(message)
			.map(m -> m.replace("\r\n", "\n"))
			.orElse(message);
	}

	public static String cleanMobileNumber(String mobileNumber) {
		// Remove hyphens and spaces
		mobileNumber = mobileNumber
			.replace("-", "")
			.replace(" ", "");

		// Denmark +45
		// Sweden +46
		// Norway +47
		// Finland +358
		// Czech Republic +420

		// Replace "00" with "+"
		if (mobileNumber.startsWith("00")) {
			mobileNumber = "+" + mobileNumber.substring(2);
		}

		// Do some additional Swedish number "handling"
		if (mobileNumber.startsWith("07")) {
			mobileNumber = "+46" + mobileNumber.substring(1);
		}
		if (mobileNumber.startsWith("+4607")) {
			mobileNumber = mobileNumber.replace("+4607", "+467");
		}

		return mobileNumber;
	}

	public static Sender cleanSenderName(final Sender sender) {
		var name = sender.getName()
			.replaceAll("[åä]", "a")
			.replaceAll("[ö]", "o")
			.replaceAll("[ÅÄ]", "A")
			.replaceAll("[Ö]", "O");
		sender.setName(name);
		return sender;
	}

}
