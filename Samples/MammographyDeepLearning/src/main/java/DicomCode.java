/**
 * SPDX-FileCopyrightText: 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
 * SPDX-License-Identifier: GPL-3.0-or-later
 **/

/**
 * Java plugin for Orthanc
 * Copyright (C) 2023-2024 Sebastien Jodogne, UCLouvain, Belgium
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 **/


import org.json.JSONObject;

class DicomCode {
    private static final String CODE_VALUE = "0008,0100";
    private static final String CODING_SCHEME_DESIGNATOR = "0008,0102";
    private static final String CODE_MEANING = "0008,0104";

    private String codeValue;
    private String codingSchemeDesignator;
    private String codeMeaning;

    public DicomCode(String codeValue,
                     String codingSchemeDesignator,
                     String codeMeaning) {
        this.codeValue = codeValue;
        this.codingSchemeDesignator = codingSchemeDesignator;
        this.codeMeaning = codeMeaning;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public String getCodingSchemeDesignator() {
        return codingSchemeDesignator;
    }

    public String getCodeMeaning() {
        return codeMeaning;
    }

    public JSONObject toJson() {
        return new JSONObject().
                put(CODE_VALUE, codeValue).
                put(CODING_SCHEME_DESIGNATOR, codingSchemeDesignator).
                put(CODE_MEANING, codeMeaning);
    }
}
