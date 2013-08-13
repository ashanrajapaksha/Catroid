/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.speechrecognition;

import android.os.Bundle;

public abstract interface RecognizerCallback {

	public static final int RESULT_NOMATCH = 0x1;
	public static final int RESULT_OK = 0x2;

	public static final String RESULT_BUNDLE_MATCHES = "RESULT";
	public static final String ERROR_BUNDLE_MESSAGE = "ERROR_MESSAGE";
	public static final String ERROR_BUNDLE_CODE = "ERROR_CODE";
	public static final String BUNDLE_IDENTIFIER = "IDENTIFIER";

	public abstract void onRecognizerResult(int resultCode, Bundle resultBundle);

	public abstract void onRecognizerError(Bundle errorBundle);

}
