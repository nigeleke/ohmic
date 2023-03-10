/*
 * Copyright (c) 2023, Nigel Eke
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.nigeleke.ohmic
package app

import core.*
import document.*

import java.nio.file.Paths

import scala.jdk.CollectionConverters.*

object ResistorApp:

  private val series = Resistor.setFor(Series.e24)

  private val series5BandMap = (Resistor.fullSet
    .filter(series.contains)
    .filter(_.colourCodes.size == 4)
    .map(r => (r.value, r)))
    .toMap

  private val rows = for
    r1 <- series.toList.sortBy(_.value)
    optR2 = series5BandMap.get(r1.value)
  yield
    val v = r1.formattedValue
    val colourCodes =
      Seq(r1.colourCodes) :+ (optR2.map(_.colourCodes).getOrElse(Seq.empty[BandColour]))
    ResistorRow(v.value, v.unit.symbol, colourCodes)

  @main
  def main =
    ResistorDocument()
      .withRows(rows.toSeq)
      .saveAs(Paths.get("resistors.xlsx"))
