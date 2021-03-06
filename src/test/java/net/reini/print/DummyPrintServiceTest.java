/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018, 2019 Patrick Reinhart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.reini.print;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.PrinterState;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DummyPrintServiceTest {
  private DummyPrintService printerService;

  @BeforeEach
  public void setUp() {
    printerService = new DummyPrintService("PrinterName");
  }

  @Test
  public void testGetName() {
    assertEquals("PrinterName", printerService.getName());
  }

  @Test
  public void testCreatePrintJob() {
    DocPrintJob printerjob = printerService.createPrintJob();
    assertNotNull(printerjob);
  }

  @Test
  public void testIsDocFlavorSupported() {
    assertTrue(printerService.isDocFlavorSupported(DocFlavor.SERVICE_FORMATTED.PAGEABLE));
    assertTrue(printerService.isDocFlavorSupported(DocFlavor.SERVICE_FORMATTED.PRINTABLE));
  }

  @Test
  public void testGetAttribute() {
    PrinterName printerName = (PrinterName) printerService.getAttribute(PrinterName.class);
    assertNotNull(printerName);
    assertEquals("PrinterName", printerName.getValue());

    PrinterState printerState = (PrinterState) printerService.getAttribute(PrinterState.class);
    assertEquals(PrinterState.IDLE, printerState);
  }

  @Test
  public void testGetAttributes() {
    PrintServiceAttributeSet attributes = printerService.getAttributes();

    assertTrue(attributes.containsValue(new PrinterName("PrinterName", null)));
    assertTrue(attributes.containsValue(PrinterState.IDLE));
  }

  @Test
  public void testGetSupportedDocFlavors() {
    List<DocFlavor> docFlavors = Arrays.asList(printerService.getSupportedDocFlavors());

    assertTrue(docFlavors.contains(DocFlavor.SERVICE_FORMATTED.PAGEABLE));
    assertTrue(docFlavors.contains(DocFlavor.SERVICE_FORMATTED.PRINTABLE));
  }

  @Test
  public void testGetSupportedAttributeCategories() {
    Class<?>[] categories = printerService.getSupportedAttributeCategories();
    assertNotNull(categories);
    assertEquals(0, categories.length);
  }

}
