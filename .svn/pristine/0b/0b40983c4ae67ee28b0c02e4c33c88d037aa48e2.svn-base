package com.cb.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.domain.Region;
import com.cb.bos.domain.Subarea;
import com.cb.bos.utils.FileUtils;
import com.cb.bos.web.action.base.BaseAction;

/**
 * 
*<p>Title:SubareaAction</p>	
*<p>Description:分区</p>	
*@author Eason
*@Time 2017年8月26日上午11:19:51
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {

	public String addSubarea(){
		
		subareaService.saveSubarea(this.getModel());
		return "list";
	}
	
	
	public String pageQuery() throws Exception{
		//查询之前封装条件
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
		
		if(StringUtils.isNotBlank(getModel().getAddresskey())){
			//按照地址关键字模糊查询
			detachedCriteria2.add(Restrictions.like("addresskey", "%"+getModel().getAddresskey()+"%"));
		}
		
		Region region = getModel().getRegion();
		if(region != null){
			//创建别名 用于联合多表查询
			detachedCriteria2.createAlias("region", "r");
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			System.out.println(province);
			if(StringUtils.isNotBlank(province)){
				detachedCriteria2.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			if(StringUtils.isNotBlank(city)){
				detachedCriteria2.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if(StringUtils.isNotBlank(district)){
				detachedCriteria2.add(Restrictions.like("r.district", "%"+district+"%"));
			}
			
		}
//-----------------------------------------------------------------------------------//
		subareaService.pageQuery(pageBean);
		String[] excludes = 
				new String[]{"currentPage","pageSize","detachedCriteria","subareas","decidedzone"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	/**
	 * 分区导出 xls表
	 * @throws IOException 
	 */
	public String expotXls() throws IOException{
		List<Subarea> list = subareaService.findAll();
		//在内存中创建一个Excel表文件，通过输出流写到客户端下载
		HSSFWorkbook workbook = new HSSFWorkbook(); //相当于创建了一个Excel表
		//创建一个sheet页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		//创建第一行 
		HSSFRow headrow = sheet.createRow(0);
		headrow.createCell(0).setCellValue("分区编号");
		headrow.createCell(1).setCellValue("区域编号");
		headrow.createCell(2).setCellValue("起始号");
		headrow.createCell(3).setCellValue("终止号");
		headrow.createCell(4).setCellValue("位置");
		headrow.createCell(5).setCellValue("省市区");
		
		for (Subarea subarea : list) {
			HSSFRow datarow = sheet.createRow(sheet.getLastRowNum()+1);
			datarow.createCell(0).setCellValue(subarea.getId());
			datarow.createCell(1).setCellValue(subarea.getRegion().getId());
			datarow.createCell(2).setCellValue(subarea.getStartnum());
			datarow.createCell(3).setCellValue(subarea.getEndnum());
			datarow.createCell(4).setCellValue(subarea.getPosition());
			datarow.createCell(5).setCellValue(subarea.getRegion().getProvince()+subarea.getRegion().getCity()+subarea.getRegion().getDistrict());
		}
		
		//设置文件名 
		String fileName = "分区数据.xls";
		//得到浏览器信息
		String header = ServletActionContext.getRequest().getHeader("User-Agent");
		System.out.println(header);
		//设置文件编码 动态防止文件名乱码
		fileName = FileUtils.encodeDownloadFilename(fileName, header);
		System.out.println(fileName);
		//一个流两个头
		ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
		//设置文件编码
		String type = ServletActionContext.getServletContext().getMimeType(fileName);
		ServletActionContext.getResponse().setContentType(type);
		
		//设置下载方式
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;fileName="+fileName);
		workbook.write(outputStream);
		return NONE;
	}
	
	/**
	 * 查询未关联定区的分区
	 * @return
	 * @throws IOException 
	 */
	public String listAjax() throws IOException{
		List<Subarea> list = subareaService.findListNotAssociation();
		
		String[] excludes = 
				new String[]{"decidedzone","region","id","endnum","single","startnum"};
		this.writeList2Json(list, excludes );
		return NONE;
	}
	
	
	
	
	
	
}
