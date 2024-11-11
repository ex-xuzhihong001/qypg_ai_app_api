package com.rxy.qypg.common.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.VoidResult;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class OssClient {
    protected static org.slf4j.Logger Logger = LoggerFactory.getLogger("OssClient");
	private static OSSClient _ossClient;
	private static ClientConfiguration conf;
	static {
		conf = new ClientConfiguration();
		// 设置HTTP最大连接数为20
		conf.setMaxConnections(20);
		// 设置TCP连接超时为5000毫秒
		conf.setConnectionTimeout(50000);
		// 设置最大的重试次数为5
		conf.setMaxErrorRetry(5);
		// 设置Socket传输数据超时的时间为5000毫秒
		conf.setSocketTimeout(30000);
	}


	public static OSSClient get_ossClient(String accessKeyId, String accessKeySecret, String endpoint) {
		_ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
		return _ossClient;
	}

	/**
	 * 上传到阿里云OSS空间 此方法只负责从服务器上传至阿里OSS
	 * @param originFullPath 文件在服务器的全路径：/home/work/temp/11111.jpg
	 * @param fileName 上传到OSS后，访问路径：upload/11111.jpg
	 * @throws FileNotFoundException
	 */
	public static void uploadObject(OSSClient ossClient, InputStream inputStream, Long fileLength, String fileName, String bucketName) throws FileNotFoundException {
		ObjectMetadata meta = new ObjectMetadata();
		// 必须设置ContentLength
		meta.setContentLength(fileLength);
		PutObjectResult result = ossClient.putObject(bucketName, fileName, inputStream, meta);
	}


	/**
	 * 删除阿里云OSS的文件
	 * @param fileName 访问路径：upload/11111.jpg
	 * @throws FileNotFoundException
	 */
	public static void deleteObject(OSSClient ossClient, String fileName, String bucketName) {
		VoidResult voidResult = ossClient.deleteObject(bucketName, fileName);
	}


	public static void main(String[] args) {
		try {
			OSSClient ossClient = get_ossClient("LTAI5tS3vsu1N1Hu4M5M39cp", "bUiIRgIx2SeloUtFhxhUooce9qw2ib", "oss-cn-shanghai.aliyuncs.com");
			/*String path = "C:\\Users\\Administrator\\Desktop\\bufan_user_default.jpg";
			uploadObject(ossClient,path,"upload/bufan_user_default.jpg","bufan-beta");
			deleteObject(ossClient,"upload/bufan_user_default.jpg","bufan-beta");*/
			String path = "C:\\Users\\Administrator\\Desktop\\抽卡机素材整理(1)\\抽卡机素材整理\\背景音乐.mp3";
			File file = new File(path);
			InputStream content = new FileInputStream(file);
			uploadObject(ossClient,content,file.length(),"upload/bufan_user_default.mp3","bufan-beta");
			deleteObject(ossClient,"upload/bufan_user_default.mp3","bufan-beta");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
