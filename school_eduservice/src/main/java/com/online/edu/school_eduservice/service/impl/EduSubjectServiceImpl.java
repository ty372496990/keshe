package com.online.edu.school_eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.school_eduservice.entity.EduSubject;
import com.online.edu.school_eduservice.entity.vo.SubjectNestedVo;
import com.online.edu.school_eduservice.entity.vo.SubjectVo;
import com.online.edu.school_eduservice.mapper.EduSubjectMapper;
import com.online.edu.school_eduservice.service.EduSubjectService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ty
 * @since 2020-02-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> importSubject(MultipartFile file) {
        //存储错误信息
        List<String> msg = new ArrayList<>();
        try {

            //获取文件输入流
            InputStream inputStream = file.getInputStream();

            //根据输入流获取workbook
            Workbook workbook = new XSSFWorkbook(inputStream);
            //根据workbook获取sheet
            Sheet sheet = workbook.getSheetAt(0);
            //根据sheet获取row
            //从第二行获取
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if(row == null){
                    String string = "表格为空！";
                    msg.add(string);
                    continue;
                }
                //根据row获取cell
                Cell cellOne = row.getCell(0);
                if(cellOne == null){
                    String string = "表格第"+i+"行第1列为空";
                    msg.add(string);
                    continue;
                }
                //根据cell获取值
                //一级分类的值
                String stringCellOneValue = cellOne.getStringCellValue();
                EduSubject eduSubject1 = existSubject(stringCellOneValue);
                String idParent = null;//一级分类的id
                if(eduSubject1 == null){//如果不存在就添加
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(stringCellOneValue);
                    eduSubject.setParentId("0");
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
                    //获取一级分类的id
                    idParent = eduSubject.getId();
                }else{//如果存在，则把一级分类id值存储下来
                    idParent = eduSubject1.getId();
                }
                Cell cellTwo = row.getCell(1);
                if(cellTwo == null){
                    String string = "表格第"+i+"行第2列为空";
                    msg.add(string);
                    continue;
                }
                //二级分类的值
                String stringTwoCellValue = cellTwo.getStringCellValue();
                EduSubject eduSubject2 = existSecondSubject(stringTwoCellValue,idParent);
                if(eduSubject2 == null){
                    EduSubject eduSubjectTwo = new EduSubject();
                    eduSubjectTwo.setTitle(stringTwoCellValue);
                    eduSubjectTwo.setParentId(idParent);
                    eduSubjectTwo.setSort(0);
                    baseMapper.insert(eduSubjectTwo);
                }
            }

            //将获取的值保存入数据库
            return msg;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }


    //判断是否有二级分类
    private EduSubject existSecondSubject(String name, String idParent){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",idParent);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
    //判断是否有一级分类
    private EduSubject existSubject(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }



    @Override
    public List<SubjectNestedVo> nestlist() {
        //最终要的到的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();

        //获取一级分类数据记录
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<EduSubject> subjects = baseMapper.selectList(queryWrapper);

        //获取二级分类数据记录
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", 0);
        queryWrapper2.orderByAsc("sort", "id");
        List<EduSubject> subSubjects = baseMapper.selectList(queryWrapper2);

        //填充一级分类vo数据
        int count = subjects.size();
        for (int i = 0; i < count; i++) {
            //遍历一级列表数据
            EduSubject subject = subjects.get(i);

            //创建一级类别vo对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(subject, subjectNestedVo);
            subjectNestedVoArrayList.add(subjectNestedVo);

            //填充二级分类vo数据
            ArrayList<SubjectVo> subjectVoArrayList = new ArrayList<>();
            int count2 = subSubjects.size();
            for (int j = 0; j < count2; j++) {
                //遍历二级列表
                EduSubject subSubject = subSubjects.get(j);
                if(subject.getId().equals(subSubject.getParentId())){

                    //创建二级类别vo对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject, subjectVo);
                    subjectVoArrayList.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subjectVoArrayList);
        }


        return subjectNestedVoArrayList;
    }

    @Override
    public boolean removeSubjectById(String id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer integer = baseMapper.selectCount(wrapper);
        if(integer > 0) {
            return false;
        }else {
            int result = baseMapper.deleteById(id);
            return result > 0;
        }
    }

    @Override
    public boolean addFirstLevel(EduSubject eduSubject) {
        EduSubject eduSubject1 = this.existSubject(eduSubject.getId());
        if(eduSubject1 == null) {
            eduSubject.setParentId("0");
            int insert = baseMapper.insert(eduSubject);
            return insert > 0;
        }
        return false;
    }

    @Override
    public boolean addSecondLevel(EduSubject eduSubject) {
        EduSubject eduSubject2 = this.existSecondSubject(eduSubject.getTitle(),eduSubject.getParentId());
        if(eduSubject2 == null) {
            int insert = baseMapper.insert(eduSubject);
            return insert > 0;
        }
        return false;
    }
}
