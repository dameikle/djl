/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.ndarray;

import ai.djl.Device;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NDListTest {

    @Test
    public void testNumpy() throws IOException {
        try (NDManager manager = NDManager.newBaseManager(Device.cpu())) {
            byte[] data = NDSerializerTest.readFile("list.npz");
            NDList decoded = NDList.decode(manager, data);

            ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length + 1);
            decoded.encode(bos, NDList.Encoding.NPZ);
            NDList list = NDList.decode(manager, bos.toByteArray());
            Assert.assertEquals(list.size(), 2);
            Assert.assertEquals(list.get(0).getName(), "bool8");
        }
    }

    @Test
    public void testSafetensors() throws IOException {
        try (NDManager manager = NDManager.newBaseManager(Device.cpu())) {
            byte[] data = NDSerializerTest.readFile("list.safetensors");
            NDList decoded = NDList.decode(manager, data);

            ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length + 1);
            decoded.encode(bos, NDList.Encoding.SAFETENSORS);
            NDList list = NDList.decode(manager, bos.toByteArray());
            Assert.assertEquals(list.size(), 2);
            Assert.assertEquals(list.get(0).getName(), "attention");
            Assert.assertEquals(list.get(0).toByteArray(), new byte[] {0, 1, 2, 3, 4, 5});
        }
    }
}
