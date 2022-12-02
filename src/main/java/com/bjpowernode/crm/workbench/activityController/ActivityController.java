package com.bjpowernode.crm.workbench.activityController;

import com.bjpowernode.crm.commons.bean.ReturnObject;
import com.bjpowernode.crm.commons.constants.Constants;
import com.bjpowernode.crm.commons.utils.FormatDateTime;
import com.bjpowernode.crm.commons.utils.HSSFUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/6 20:05
 */
@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemark;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> userslist = userService.queryAllUsers();
        //得到列表在前台创建活动的模态窗口中展示
        request.setAttribute("userslist",userslist);
        return "workbench/activity/index";
    }
    @RequestMapping("/workbench/activity/savaCreateActivity/index.do")
    public @ResponseBody Object saveActivity(Activity activity, HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_OBJECT);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(FormatDateTime.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        //调用Service
        ReturnObject returnObject = new ReturnObject();
        try{
            int res = activityService.saveCreatActivity(activity);
            if (res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        } catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }

        return returnObject;
    }
    @RequestMapping("/workbench/activity/queryActivityByCondition.do")
    @ResponseBody
    public Object queryActivityByCondition(String name,String owner, String startDate,String endDate,
                                           int pageNum,int pageSize){
        //封装参数，供数据库调用
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNum",(pageNum-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("activityList",activityList);
        resultMap.put("totalRows",totalRows);
        return resultMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivityByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        //增删改都要加上事务
        try {
            int res = activityService.deleteActivityByIds(id);
            if (res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        } catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    @ResponseBody
    public Object queryActivityById(String id) {
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivityById.do")
    @ResponseBody
    public Object saveEditActivityById(Activity activity,HttpSession session){
        User user= (User) session.getAttribute(Constants.SESSION_OBJECT);
        activity.setEditTime(FormatDateTime.formatDateTime(new Date()));
        activity.setEditBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int res = activityService.saveEditActivityById(activity);
            if (res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else{
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        } catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public void exportAllActivity(HttpServletResponse response) throws IOException {
        List<Activity> activities = activityService.queryAllActivity();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);  cell.setCellValue("ID");
        cell = row.createCell(1);   cell.setCellValue("所有者");
        cell=row.createCell(2);     cell.setCellValue("名称");
        cell=row.createCell(3);     cell.setCellValue("开始日期");
        cell=row.createCell(4);     cell.setCellValue("结束日期");
        cell=row.createCell(5);     cell.setCellValue("成本");
        cell=row.createCell(6);     cell.setCellValue("描述");
        cell=row.createCell(7);     cell.setCellValue("创建时间");
        cell=row.createCell(8);     cell.setCellValue("创建者");
        cell=row.createCell(9);     cell.setCellValue("修改时间");
        cell=row.createCell(10);    cell.setCellValue("修改者");

        if (activities != null && activities.size() != 0){
            Activity activity = null;
            for (int i = 0; i < activities.size(); i++) {
                activity = activities.get(i);
                row = sheet.createRow(i+1);
                cell = row.createCell(0);   cell.setCellValue(activity.getId());
                cell=row.createCell(1);     cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);     cell.setCellValue(activity.getName());
                cell=row.createCell(3);     cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);     cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);     cell.setCellValue(activity.getCost());
                cell=row.createCell(6);     cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);     cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(8);     cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);     cell.setCellValue(activity.getEditTime());
                cell=row.createCell(10);    cell.setCellValue(activity.getEditBy());
            }
        }
        //根据workbook对象生成excel文件 然后再用输入流读取
        //再用输出流输出到浏览器(response) 这种方式非常费时

        //直接从内存输出到浏览器的输出流
        //设置响应类型和响应头
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=activityList.xls");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile,HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_OBJECT);
        ReturnObject returnObject = new ReturnObject();
        try{
            InputStream inputStream = activityFile.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            ArrayList<Activity> activities = new ArrayList<>();
            Activity activity = null;
            HSSFRow row = null;
            HSSFCell cell = null;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(FormatDateTime.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());

                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    String cellValue=HSSFUtils.getCellValueForStr(cell);
                    if(j==0){
                        activity.setName(cellValue);
                    }else if(j==1){
                        activity.setStartDate(cellValue);
                    }else if(j==2){
                        activity.setEndDate(cellValue);
                    }else if(j==3){
                        activity.setCost(cellValue);
                    }else if(j==4){
                        activity.setDescription(cellValue);
                    }
                }
                activities.add(activity);
            }

            int res = activityService.saveCreateActivityByList(activities);

            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(res);
        } catch (Exception exception){
            exception.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/lookDetailActivity.do")
    public String lookDetailActivity(String id,HttpServletRequest request){
        Activity activity = activityService.queryActivityForDetailById(id);

        List<ActivityRemark> activityRemarkList = activityRemark.selectActivityDetailByActivityId(id);
        request.setAttribute("activity",activity);
        request.setAttribute("activityRemarkList",activityRemarkList);

        return "workbench/activity/detail";
    }
}
