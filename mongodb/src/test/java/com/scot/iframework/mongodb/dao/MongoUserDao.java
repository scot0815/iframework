package com.scot.iframework.mongodb.dao;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;

@Service
public class MongoUserDao implements IMongoUserDao {

	/**
	 * mongodb 路由操作.
	 */
	@Resource(name="mongoTemplate")
	private MongoTemplate mt;

	/**
	 * 文件上传.
	 */
	@Resource(name="mongoUploadTemplate")
	private MongoTemplate mongoUploadTemplate;

	/**
	 * 测试插入.
	 */
	public void insert() {
		for (int i = 0; i < 10000; i++) {

			JSONObject jo = new JSONObject();
			jo.put("name", "jack" + i);
			jo.put("age", i);
			mt.insert(jo, "user");
		}

		for (int i = 0; i < 10000; i++) {

			JSONObject jo = new JSONObject();
			jo.put("name", "sam" + i);
			jo.put("age", i);
			mt.insert(jo, "user");
		}

		for (int i = 0; i < 10000; i++) {

			JSONObject jo = new JSONObject();
			jo.put("name", "senvon" + i);
			jo.put("age", i);
			mt.insert(jo, "user");
		}

	}

	/**
	 * 测试统计.
	 */
	public void sum() {
		DBCollection col = mt.getCollection("user");

		String mapStr = "function(){if(this.age>10) emit(this.age,this.name)}";

		String reduceStr = "function(key,values){var count=0;values.forEach(function(){count+=1;});var result={names:values,sum:count};return result;}";

		MapReduceOutput output = col.mapReduce(mapStr, reduceStr, "result",
				null);

		DBCollection outcol = output.getOutputCollection();

		DBCursor dbc = outcol.find();

		StringBuffer sb = new StringBuffer();

		while (dbc.hasNext()) {
			DBObject dbo = dbc.next();
			sb.append(dbo.toString());
			System.out.println(dbo.toString());
		}

	}

	/**
	 * 上传位置.
	 * @param fileUrl	文件路径
	 * @param uploadFileName	mongodb上文件名
	 */
	@Override
	public void upload(String fileUrl, String uploadFileName) {
		GridFS fs = new GridFS(mongoUploadTemplate.getDb());
		GridFSInputFile gfsif = null;
		File file = new File(fileUrl);
		try {
			gfsif = fs.createFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gfsif.put("filename", uploadFileName);
		gfsif.save();
	}

	/**
	 * 下载文件.
	 * @param filename	mongodb上文件名称
	 * @param downloadUrl	下载路径
	 */
	@Override
	public void download(String filename, String downloadUrl) {
		GridFS fs = new GridFS(mongoUploadTemplate.getDb());
		GridFSDBFile gridFSDBFile = fs.findOne(filename);
		InputStream inputStream = gridFSDBFile.getInputStream();
		BufferedInputStream bf = new BufferedInputStream(inputStream);
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(downloadUrl);
			int len;
			byte[] b = new byte[1024];
			while((len=bf.read(b))!=-1){
				outputStream.write(b);
			}
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {

				try {

					if(null != bf) {
						bf.close();
					}
					if(null != inputStream) {
						inputStream.close();
					}
					if(null != outputStream) {
						outputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
