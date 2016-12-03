package com.scot.iframework.mongodb.dao;

/**
 * mongodb测试dao
 */
public interface IMongoUserDao {

	/**
	 * 测试插入.
	 */
	void insert();

	/**
	 * 测试统计.
	 */
	void sum();

	/**
	 * 上传位置.
	 * @param fileUrl	文件路径
	 * @param uploadFileName	mongodb上文件名
	 */
	void upload(String fileUrl, String uploadFileName);

	/**
	 * 下载文件.
	 * @param filename	mongodb上文件名称
	 * @param downloadUrl	下载路径
	 */
	void download(String filename, String downloadUrl);
}
