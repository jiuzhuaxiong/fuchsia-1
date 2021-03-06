/*
 * #%L
 * OW2 Chameleon - Fuchsia Framework
 * %%
 * Copyright (C) 2009 - 2014 OW2 Chameleon
 * %%
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
 * #L%
 */
/*
    Calimero - A library for KNX network access
    Copyright (C) 2006-2008 B. Malinowsky

    This program is free software; you can redistribute it and/or 
    modify it under the terms of the GNU General Public License 
    as published by the Free Software Foundation; either version 2 
    of the License, or at your option any later version. 
 
    This program is distributed in the hope that it will be useful, 
    but WITHOUT ANY WARRANTY; without even the implied warranty of 
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
    GNU General Public License for more details. 
 
    You should have received a copy of the GNU General Public License 
    along with this program; if not, write to the Free Software 
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. 
 
    Linking this library statically or dynamically with other modules is 
    making a combined work based on this library. Thus, the terms and 
    conditions of the GNU General Public License cover the whole 
    combination. 
 
    As a special exception, the copyright holders of this library give you 
    permission to link this library with independent modules to produce an 
    executable, regardless of the license terms of these independent 
    modules, and to copy and distribute the resulting executable under terms 
    of your choice, provided that you also meet, for each linked independent 
    module, the terms and conditions of the license of that module. An 
    independent module is a module which is not derived from or based on 
    this library. If you modify this library, you may extend this exception 
    to your version of the library, but you are not obligated to do so. If 
    you do not wish to do so, delete this exception statement from your 
    version. 
*/
	 
package tuwien.auto.calimero.knxnetip.util;

import tuwien.auto.calimero.exception.KNXFormatException;

/**
 * Description Information Block (DIB).
 * <p>
 * A DIB is used to return device specific information.<br>
 * This DIB is a common base for more detailed description formats contained in DIBs. For
 * usage of the different description information available, refer to the DIB subtypes.
 * <p>
 * The currently known valid descriptor type codes (KNXnet/IP core specification v1.2) are
 * defined as available DIB constants.
 * 
 * @author B. Malinowsky
 */
public abstract class DIB
{
	/**
	 * Description type code for device information e.g. KNX medium.
	 * <p>
	 */
	public static final short DEVICE_INFO = 0x01;

	/**
	 * Description type code for further data defined by device manufacturer.
	 * <p>
	 */
	public static final short MFR_DATA = 0xFE;

	/**
	 * Description type code for service families supported by the device.
	 * <p>
	 */
	public static final short SUPP_SVC_FAMILIES = 0x02;

	final short size;
	final short type;

	/**
	 * Creates a new DIB out of a byte array.
	 * <p>
	 * 
	 * @param data byte array containing DIB structure
	 * @param offset start offset of DIB in <code>data</code>
	 * @throws KNXFormatException if no DIB found or invalid structure
	 */
	protected DIB(byte[] data, int offset) throws KNXFormatException
	{
		if (data.length - offset < 2)
			throw new KNXFormatException("buffer too short for DIB header");
		size = (short) (data[offset] & 0xFF);
		type = (short) (data[offset + 1] & 0xFF);
		if (size > data.length - offset)
			throw new KNXFormatException("DIB size bigger than actual data length", size);
	}

	/**
	 * Returns the description type code of this DIB.
	 * <p>
	 * The type code specifies which kind of description information is contained in the
	 * DIB.
	 * 
	 * @return description type code as unsigned byte
	 */
	public final short getDescTypeCode()
	{
		return type;
	}

	/**
	 * Returns the structure length of this DIB in bytes.
	 * <p>
	 * 
	 * @return structure length as unsigned byte
	 */
	public final short getStructLength()
	{
		return size;
	}

	/**
	 * Returns the byte representation of the whole DIB structure.
	 * <p>
	 * 
	 * @return byte array containing structure
	 */
	public byte[] toByteArray()
	{
		final byte[] buf = new byte[size];
		buf[0] = (byte) size;
		buf[1] = (byte) type;
		return buf;
	}
}
