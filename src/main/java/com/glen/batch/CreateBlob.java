/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * EDITING INSTRUCTIONS
 * This file is referenced in READMEs and javadoc. Any change to this file should be reflected in
 * the project's READMEs and package-info.java.
 */


package com.glen.batch;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.Storage.BlobGetOption;
import com.google.cloud.storage.Storage.BucketGetOption;

/**
 * A snippet for Google Cloud Storage showing how to create a blob.
 */


public class CreateBlob {

  public static void main(String[] args) {
	long bucketMetageneration = 1;
	System.out.println(args[0]);
    Storage storage = StorageOptions.getDefaultInstance().getService();
    Bucket bucket =storage.get("myfirstbucketcreatejava", BucketGetOption.metagenerationMatch(bucketMetageneration));
    
    if(args[0].equals("CBL"))
    {
    	System.out.println(args[1]);
    	writeBlackList(bucket,args[1]);
    }
    else if(args[0].equals("VW"))
    {
    	viewBL(bucket);
    }
    
     /*
    BlobId blobId = BlobId.of("bucket", "blob_name");
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
    Blob blob = storage.create(blobInfo, "Hello, Cloud Storage!".getBytes(UTF_8));*/
    // Creates the new bucket
   // Bucket bucket = storage.create(BucketInfo.of(args[0]));
 //   Blob blacklist= bucket.get("TA2-0-BlackList.txt", null);
  //  byte[] blackListContent= blacklist.getContent(null);
  
    
  /*  System.out.printf("Bucket %s created.%n", bucket.getName());
    InputStream content = new ByteArrayInputStream("Hello, World!".getBytes(UTF_8));
    Blob blob = bucket.create("chair", content, "text/plain");*/
  }
  
  private static void writeBlackList( Bucket bucket,String content)
  {
	  long generation = 4322;
	  System.out.println("Called WriteBlackList.");
	  Blob blacklist= bucket.get("TA2-0-BlackList.txt",BlobGetOption.generationNotMatch(generation));
	 String contentAppended=  getBL(bucket)+content;
	  byte[] newblacklist=contentAppended.getBytes();
	    try (WriteChannel writer = blacklist.writer()) {
	    	   try {
	    	     writer.write(ByteBuffer.wrap(newblacklist, 0, newblacklist.length));
	    	   } catch (Exception ex) {
	    		   ex.printStackTrace();
	    	   }
	    	   writer.close();
	    	 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    System.out.println("Ending WriteBlackList.");
  }
  
  private static void viewBL(Bucket bucket)
  {
	  System.out.println("Called viewBL.");
	  long generation = 4342;
	//  Blob blacklist= bucket.get("TA2-0-BlackList.txt",BlobGetOption.generationMatch(generation));
	  Blob blacklist= bucket.get("TA2-0-BlackList.txt",BlobGetOption.generationNotMatch(generation));
	//  Blob blacklist2= bucket.get("TA2-0-BlackList.txt",BlobGetOption.generationNotMatch(generation));
	    byte[] blackListContent= blacklist.getContent(BlobSourceOption.generationMatch());
	    String value = new String(blackListContent);
	    System.out.println(value);
	 //   byte[] blackListContent2= blacklist2.getContent(BlobSourceOption.generationNotMatch());
	  //  System.out.println(blackListContent2.toString());
	    
  }
  
  private static String getBL(Bucket bucket)
  {
	  System.out.println("Called viewBL.");
	  long generation = 4342;
	//  Blob blacklist= bucket.get("TA2-0-BlackList.txt",BlobGetOption.generationMatch(generation));
	  Blob blacklist= bucket.get("TA2-0-BlackList.txt",BlobGetOption.generationNotMatch(generation));
	//  Blob blacklist2= bucket.get("TA2-0-BlackList.txt",BlobGetOption.generationNotMatch(generation));
	    byte[] blackListContent= blacklist.getContent(BlobSourceOption.generationMatch());
	    String value = new String(blackListContent);
	   // System.out.println(value);
	    return value;
	 //   byte[] blackListContent2= blacklist2.getContent(BlobSourceOption.generationNotMatch());
	  //  System.out.println(blackListContent2.toString());
	    
  }
}

