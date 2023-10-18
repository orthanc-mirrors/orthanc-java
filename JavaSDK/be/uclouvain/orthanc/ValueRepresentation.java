package be.uclouvain.orthanc;

/**
 * SPDX-FileCopyrightText: 2023 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023 Sebastien Jodogne, UCLouvain, Belgium
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 **/


/**
 * The value representations present in the DICOM standard (version 2013).
 **/
public enum ValueRepresentation {
    /**
     * Application Entity
     **/
    AE(1),
    /**
     * Age String
     **/
    AS(2),
    /**
     * Attribute Tag
     **/
    AT(3),
    /**
     * Code String
     **/
    CS(4),
    /**
     * Date
     **/
    DA(5),
    /**
     * Decimal String
     **/
    DS(6),
    /**
     * Date Time
     **/
    DT(7),
    /**
     * Floating Point Double
     **/
    FD(8),
    /**
     * Floating Point Single
     **/
    FL(9),
    /**
     * Integer String
     **/
    IS(10),
    /**
     * Long String
     **/
    LO(11),
    /**
     * Long Text
     **/
    LT(12),
    /**
     * Other Byte String
     **/
    OB(13),
    /**
     * Other Float String
     **/
    OF(14),
    /**
     * Other Word String
     **/
    OW(15),
    /**
     * Person Name
     **/
    PN(16),
    /**
     * Short String
     **/
    SH(17),
    /**
     * Signed Long
     **/
    SL(18),
    /**
     * Sequence of Items
     **/
    SQ(19),
    /**
     * Signed Short
     **/
    SS(20),
    /**
     * Short Text
     **/
    ST(21),
    /**
     * Time
     **/
    TM(22),
    /**
     * Unique Identifier (UID)
     **/
    UI(23),
    /**
     * Unsigned Long
     **/
    UL(24),
    /**
     * Unknown
     **/
    UN(25),
    /**
     * Unsigned Short
     **/
    US(26),
    /**
     * Unlimited Text
     **/
    UT(27);

    private int value;

    private ValueRepresentation(int value) {
        this.value = value;
    }

    /**
     * Return the enumeration value that corresponds to an integer value of interest.
     * @param value The integer value.
     * @return The enumeration value.
     **/
    protected static ValueRepresentation getInstance(int value) {
        if (value == 1) {
            return AE;
        }
        if (value == 2) {
            return AS;
        }
        if (value == 3) {
            return AT;
        }
        if (value == 4) {
            return CS;
        }
        if (value == 5) {
            return DA;
        }
        if (value == 6) {
            return DS;
        }
        if (value == 7) {
            return DT;
        }
        if (value == 8) {
            return FD;
        }
        if (value == 9) {
            return FL;
        }
        if (value == 10) {
            return IS;
        }
        if (value == 11) {
            return LO;
        }
        if (value == 12) {
            return LT;
        }
        if (value == 13) {
            return OB;
        }
        if (value == 14) {
            return OF;
        }
        if (value == 15) {
            return OW;
        }
        if (value == 16) {
            return PN;
        }
        if (value == 17) {
            return SH;
        }
        if (value == 18) {
            return SL;
        }
        if (value == 19) {
            return SQ;
        }
        if (value == 20) {
            return SS;
        }
        if (value == 21) {
            return ST;
        }
        if (value == 22) {
            return TM;
        }
        if (value == 23) {
            return UI;
        }
        if (value == 24) {
            return UL;
        }
        if (value == 25) {
            return UN;
        }
        if (value == 26) {
            return US;
        }
        if (value == 27) {
            return UT;
        }

        throw new IllegalArgumentException("Value out of range for enumeration ValueRepresentation: " + value);
    }

    /**
     * Get the integer value corresponding to this enumeration value.
     * @return The integer value.
     **/
    public int getValue() {
        return value;
    }
}
