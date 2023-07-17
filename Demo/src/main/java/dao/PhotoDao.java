package dao;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class PhotoDao {
    public static List<FileItem> getRequsetFileItems(HttpServletRequest request,ServletContext servletContext){
        boolean isMultipart=ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            String str="javax.servelet.context.tempdir";
            File repository=(File) servletContext.getAttribute(str);
            factory.setRepository(repository);
            ServletFileUpload upload=new ServletFileUpload(factory);
            try {
                return upload.parseRequest(request);
            }catch (FileUploadException e) {
                // TODO: handle exception
                return null;
            }
        }else {
            return null;
        }
    }
    public static boolean saveFile(FileItem item,String fileName,String saveUrl) {        
        File savePath=new File(saveUrl);
        if(!savePath.exists()) {
            savePath.mkdirs();
        }
        File uploadFile=new File(savePath+File.separator+fileName);
        try{
            item.write(uploadFile);
            System.out.println("保存文件成功");
            return true;
        }catch(Exception e){
            System.out.println("保存文件失败");
        }
        return false;
    }
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        System.out.println(timestamp);
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }
    
     public static String getPhotoNewName() {
         Date date=new Date();
         int second=getSecondTimestamp(date);
         String fileName=String.valueOf(second)+".jpg";
         return fileName;
    }
     
     public static boolean isGif(FileItem item) {
        String fileFullName=item.getName();
         File fileInfo=new File(fileFullName);
         String suffix = fileInfo.getName().substring(fileInfo.getName().lastIndexOf(".") + 1);
         if(suffix.equals("jpg")) {
             return true;
         }
         return false;        
     }
}
