package com.soldev.fieldservice.utilities;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soldev.fieldservice.dataentity.EnvironmentCheckListForJob;
import com.soldev.fieldservice.dataentity.EnvironmentCheckListForJobGroup;
import com.soldev.fieldservice.dataentity.EnvironmentCheckListForJobListByTaskType;
import com.soldev.fieldservice.dataentity.EquipmentForJob;
import com.soldev.fieldservice.dataentity.EquipmentForJobGroup;
import com.soldev.fieldservice.dataentity.EquipmentForJobListByTaskType;
import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.dataentity.TaskGroupMaster;
import com.soldev.fieldservice.dataentity.TaskTypeMaster;
import com.soldev.fieldservice.dataentity.WorkingCheckListForJob;
import com.soldev.fieldservice.dataentity.WorkingCheckListForJobGroup;
import com.soldev.fieldservice.dataentity.WorkingCheckListForJoblistByTaskType;

import java.util.ArrayList;
import java.util.List;

public class GenerateData {
    private DatabaseReference databaseReference;

    public GenerateData(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void generateEquipmentMasterTemplate() {
//        generateTaskGourp();
//
//        generateEquipmentPump();
//        generateEnvironmentPump();
//        generateWorkingPump();
//
//
//        generateEquipmentPumpBoom(2,"102","ปั๊ม บูม 32 เมตร");
//        generateEquipmentPumpBoom(3,"103","ปั๊ม บูม 38 เมตร");
//        generateEquipmentPumpBoom(4,"104","ปั๊ม บูม 42 เมตร");
//        generateEquipmentPumpBoom(5,"105","ปั๊ม บูม 43 เมตร");
//        generateEnvironmentPump102();
//        generateWorkingPump102();
//        generateEnvironmentPump103();
//        generateWorkingPump103();
//        generateEnvironmentPump104();
//        generateWorkingPump104();
//        generateEnvironmentPump105();
//        generateWorkingPump105();

//        generateEquipmentCut201(0,"201","ปาดกล่อง","ปาดกล่อง");
//        generateEquipmentCut202(1,"202","ปาดสามเหลี่ยม","ปาดสามเหลี่ยม");
//        generateEquipmentCut203(2,"203","ปาด Laser Screed","ปาด Laser Screed");
//        generateEquipmentCut204(3,"204","ปาด Truss Screed","ปาด Truss Screed");
//        generateEquipmentCut205(4,"205","ปาด Magic Screed","ปาด Magic Screed");
//////201 202 ไม่มีตรวจสอบ
//        generateEnvironmentCut203(2,"203","ปาด Laser Screed");
//        generateEnvironmentCut204(3,"204","ปาด Truss Screed");
//        generateEnvironmentCut205(4,"205","ปาด Magic Screed");




//        generateEquipmentPolish(0,"301","ขัดหยาบ","ขัดหยาบ");
//        generateEquipmentPolish(1,"302","ขัดเรียบ","ขัดเรียบ");
//        generateEquipmentPolish(2,"303","ขัดมัน","ขัดมัน");
//        generateEquipmentPolish(3,"304","ขัด burnished floor","ขัด burnished floor");
//        generateEquipmentPolish(4,"305","ขัด Floor Hardener","ขัด Floor Hardener");


//        generateEnvironmentPolish301_302_303(0,"301","ขัดหยาบ");
//        generateEnvironmentPolish301_302_303(1,"302","ขัดเรียบ");
//        generateEnvironmentPolish301_302_303(2,"303","ขัดมัน");
//        generateEnvironmentPolish301_302_303(3,"304","ขัด burnished floor");
//        generateEnvironmentPolish301_302_303(4,"305","ขัด Floor Hardener");
//
//
//        generateEquipmentOther401(0,"401","วางเหล็ก,มัดเหล็ก,เข้าแบบ","วางเหล็ก,มัดเหล็ก,เข้าแบบ");
//        generateEquipmentOther401(1,"402","ติดตั้ง Armour Joint","ติดตั้ง Armour Joint");
//        generateEquipmentOtherMobFloor(2,"403","ล้างพื้น ลงแว็กซ์","ล้างพื้น ลงแว็กซ์");
//        generateEquipmentOtherjointCut(3,"404","Saw Cut Joint","Saw Cut Joint");
//        generateEquipmentOtherpusealant(4,"405","Polyurethane Sealant","Polyurethane Sealant");
//        generateEquipmentOtherAsphalt(5,"406","หยอดยางมะตอย","หยอดยางมะตอย");
//        generateEquipmentOtherEpoxy(6,"407","Epoxy Injection","Epoxy Injection");
//        generateEquipmentOtherCutElectricandWind(7,"408","ตัดสกัด ซ่อมพื้นคอนกรีต กรณีสกัดด้วยเครื่องลม","ตัดสกัด ซ่อมพื้นคอนกรีต กรณีสกัดด้วยเครื่องลม");
//        generateEquipmentOtherCutElectricandWind(8,"409","ตัดสกัด ซ่อมพื้นคอนกรีต กรณีสกัดด้วยสกัดไฟฟ้า","ตัดสกัด ซ่อมพื้นคอนกรีต กรณีสกัดด้วยเครื่องลม");
//        generateEquipmentOther4010(9,"4010","งานทำบ้าน","งานทำบ้าน");
////
////
////
//        generateEnvironmentOther401(0,"401","วางเหล็ก,มัดเหล็ก,เข้าแบบ");
//        generateEnvironmentOther402(1,"402","ติดตั้ง Armour Joint");
//        generateEnvironmentOther403(2,"403","ล้างพื้น ลงแว็กซ์");
//        generateEnvironmentOther404(3,"404","Saw Cut Joint");
//        generateEnvironmentOther405(4,"405","Polyurethane Sealant");
//        generateEnvironmentOther407(6,"407","Epoxy Injection");
//        generateEnvironmentOther408(7,"408","ตัดสกัด ซ่อมพื้นคอนกรีต กรณีสกัดด้วยเครื่องลม");
//        generateEnvironmentOther409(8,"409","ตัดสกัด ซ่อมพื้นคอนกรีต กรณีสกัดด้วยสกัดไฟฟ้า");
//
//
//        qc201_204_205(0,"ปาดกล่อง","201");
//        qc202(1,"ปาดสามเหลี่ยม","202");
//        qc203(2,"ปาด Laser Screed","203");
//        qc201_204_205(3,"ปาด Truss Screed","204");
//        qc201_204_205(4,"ปาด Magic Screed","205");
//
//        qc301_302(0,"ขัดหยาบ","301");
//        qc301_302(1,"ขัดเรียบ","302");
//        qc303_304_305(2,"ขัดมัน","303");
//        qc303_304_305(3,"ขัด burnished floor","304");
//        qc303_304_305(4,"ขัด Floor Hardener","305");



        //สร้างเช็คลิสต์ซ่อม

//        generateEquipmentCut201(10,"4011","ซ่อมปาดกล่อง","ซ่อมปาดกล่อง");
//        generateEquipmentCut202(11,"4012","ซ่อมปาดสามเหลี่ยม","ซ่อมปาดสามเหลี่ยม");
//        generateEquipmentCut203(12,"4013","ซ่อมปาด Laser Screed","ซ่อมปาด Laser Screed");
//        generateEquipmentCut204(13,"4014","ซ่อมปาด Truss Screed","ซ่อมปาด Truss Screed");
//        generateEquipmentCut205(14,"4015","ซ่อมปาด Magic Screed","ซ่อมปาด Magic Screed");
//        generateEquipmentPolish(15,"4016","ซ่อมขัดหยาบ","ซ่อมขัดหยาบ");
//        generateEquipmentPolish(16,"4017","ซ่อมขัดเรียบ","ซ่อมขัดเรียบ");
//        generateEquipmentPolish(17,"4018","ซ่อมขัดมัน","ซ่อมขัดมัน");
//        generateEquipmentPolish(18,"4019","ซ่อมขัด burnished floor","ซ่อมขัด burnished floor");
//        generateEquipmentPolish(19,"4020","ซ่อมขัด Floor Hardener","ซ่อมขัด Floor Hardener");


//////201 202 ไม่มีตรวจสอบ
//        generateEnvironmentCut203(9,"4013","ซ่อมปาด Laser Screed");
//        generateEnvironmentCut204(10,"4014","ซ่อมปาด Truss Screed");
//        generateEnvironmentCut205(11,"4015","ซ่อมปาด Magic Screed");
//        generateEnvironmentPolish301_302_303(12,"4016","ซ่อมขัดหยาบ");
//        generateEnvironmentPolish301_302_303(13,"4017","ซ่อมขัดเรียบ");
//        generateEnvironmentPolish301_302_303(14,"4018","ซ่อมขัดมัน");
//        generateEnvironmentPolish301_302_303(15,"4019","ซ่อมขัด burnished floor");
//        generateEnvironmentPolish301_302_303(16,"4020","ซ่อมขัด Floor Hardener");
    }

    private void generateEquipmentPump(){
        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"ท่อ(1.5 เมตร) ",2));
        listEQ0.add(new EquipmentForJob(1,"ท่อ(2 เมตร) ",2));
        listEQ0.add(new EquipmentForJob(2,"ท่อ(3 เมตร)",40));
        listEQ0.add(new EquipmentForJob(3,"ปะกับ",50));
        listEQ0.add(new EquipmentForJob(4,"ข้องอ 90",8));
        listEQ0.add(new EquipmentForJob(5,"ข้องอ 90 (แบบมีขา)",2));
        listEQ0.add(new EquipmentForJob(6,"ข้องอ 45",4));
        listEQ0.add(new EquipmentForJob(7,"ม้ารองท่อ (เล็ก)",10));
        listEQ0.add(new EquipmentForJob(8,"ม้ารองท่อ (ใหญ่)",5));
        listEQ0.add(new EquipmentForJob(9,"ค้อน",1));
        listEQ0.add(new EquipmentForJob(10,"ลูกบอล",1));
        listEQ0.add(new EquipmentForJob(11,"ยางรองท่อ",6));
        listEQ0.add(new EquipmentForJob(12,"รอกโยก 1 ตัน (กำมะลอ)",1));
        listEQ0.add(new EquipmentForJob(13,"สายไฮดรอลิคหัว #32 ยาว 50 เซน",1));
        listEQ0.add(new EquipmentForJob(14,"สายไฮดรอลิคหัว #32 ยาว 80 เซน",1));
        listEQ0.add(new EquipmentForJob(15,"สายไฮดรอลิคหัว #32 ยาว 90 เซน",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจ เครื่องมือ #22",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจ เครื่องมือ #24",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจ เครื่องมือ #26",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจ เครื่องมือ #27",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจ เครื่องมือ #30",1));
        listEQ1.add(new EquipmentForJob(14,"ประแจ เครื่องมือ #32",1));
        listEQ1.add(new EquipmentForJob(15,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(16,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(17,"ประแจหกเหลี่ยม #12",1));
        listEQ1.add(new EquipmentForJob(18,"ประแจหกเหลี่ยม #14",1));;
        listEQ1.add(new EquipmentForJob(19,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(20,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(21,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(22,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"จาระบี #1",1));
        listEQ2.add(new EquipmentForJob(1,"ที่อัด จาระบี ",2));
        listEQ2.add(new EquipmentForJob(2,"กระติกน้ำดื่ม+ถังน้ำ",1));

        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(1,"101","งานปั๊มลาก","อุปกรณ์ปั๊มลาก",equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child("101").setValue(equipmentForJobListByTaskType);

    }
    private void generateEquipmentPumpBoom(int recordNo,String taskCode,String taskName){
        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"ท่อ (3 เมตร)",8));
        listEQ0.add(new EquipmentForJob(1,"ปะกับ",10));
        listEQ0.add(new EquipmentForJob(2,"ข้องอ 90",2));
        listEQ0.add(new EquipmentForJob(3,"ข้องอ 90 (แบบมีขา)",1));
        listEQ0.add(new EquipmentForJob(4,"ม้ารองท่อ (ใหญ่)",2));
        listEQ0.add(new EquipmentForJob(5,"ค้อน",1));
        listEQ0.add(new EquipmentForJob(6,"ลูกบอล",1));
        listEQ0.add(new EquipmentForJob(7,"สายไฮดรอลิคหัว #32 ยาว 50 เซน",1));
        listEQ0.add(new EquipmentForJob(8,"สายไฮดรอลิคหัว #32 ยาว 80 เซน",1));
        listEQ0.add(new EquipmentForJob(9,"สายไฮดรอลิคหัว #32 ยาว 90 เซน",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจ เครื่องมือ #22",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจ เครื่องมือ #24",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจ เครื่องมือ #26",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจ เครื่องมือ #27",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจ เครื่องมือ #30",1));
        listEQ1.add(new EquipmentForJob(14,"ประแจ เครื่องมือ #32",1));
        listEQ1.add(new EquipmentForJob(15,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(16,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(17,"ประแจหกเหลี่ยม #12",1));
        listEQ1.add(new EquipmentForJob(18,"ประแจหกเหลี่ยม #14",1));;
        listEQ1.add(new EquipmentForJob(19,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(20,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(21,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(22,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"จาระบี #1",1));
        listEQ2.add(new EquipmentForJob(1,"ที่อัด จาระบี ",2));
        listEQ2.add(new EquipmentForJob(2,"กระติกน้ำดื่ม+ถังน้ำ",1));

        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(recordNo,taskCode,taskName,"Equipment for Pump",equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskCode).setValue(equipmentForJobListByTaskType);

    }



    private void generateEnvironmentPump(){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs10 = new ArrayList<>();
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup10 = new EnvironmentCheckListForJobGroup(1,"ความพร้อมเครื่องจักร",environmentCheckListForJobs10);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup10);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs20 = new ArrayList<>();
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คระบบการทำงานของปั๊มคอนกรีต",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คน๊อตล็อคหูกระต่าย",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(2,"ตรวจเช็ตกระบอกพลังเยอร์",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คน้ำมันไฮโดรลิคและสายไฮโดรลิค",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คจารบีตามจุดหมุน",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(5,"ตรวจเช็คความหนาท่อ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup20 = new EnvironmentCheckListForJobGroup(2,"ส่วนของปั๊มคอนกรีต",environmentCheckListForJobs20);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup20);


        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(0,
                "101",
                "งานปั๊มลาก",
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child("101").setValue(environmentCheckListForJobListByTaskType);

    }



    private void generateWorkingPump(){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ด้านกว้างพื้นที่จอด ไม่น้อยกว่า 8.30 เมตร",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ลักษณะการจอดปั๊ม,พื้นที่ไม่มีความเสี่ยง (ประจักษ์ด้วยสายตา)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"พื้นที่จอดสามารถรับน้ำหนักของตัวรถพร้อมคอนกรีตได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ชิ้นส่วนของปั้มต้องมีระยะห่างจากแหล่งจ่ายไฟ หรือเสาไฟฟ้าแรงสูง เกิน 5 ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"หน้างานมีพื้นที่ให้สามารถล้างท่อ,ปั้มได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"แนวการต่อท่อ ระยะการต่อท่อแนวราบ แนวดิ่งและความปลอดภัย",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ตั้งปั้ม โดยกางขาปั๊มออกสุด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ปฎิบัติตามขั้นตอน WI",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"การรั่วซึมของไฮโดรลิค",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"มีการรั่วซึมท่อส่งคอนกรีต,ซิล,ประกับ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"ใช้ม้ารองท่อถูกต้องเหมาะสมตามสภาพหน้างาน",true,false,false));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

//        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
//        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ดเตรียมการกรณีคอนกรีตเหลือท้ายปั้ม",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"Slump 13±1 cm. หรือตามมาตรฐานการผลิตคอนกรีต",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"ตั้งปั้ม โดยกางขาปั้มออกสุด",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"มีการเทต่อเนื่อง  (ล่าช้าได้ไม่เกิน30นาที)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ระยะเวลาคอนรีต จากแพลนท์ - ท้ายปั้ม (ระยะเวลาไม่เกิน 2 ชม.)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ปฎิบัติตามขั้นตอน WI",false,true,true));
//        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(1,"ตรวจสอบหลังจบงาน/ขณะปฏิบัติงาน",workingCheckListForJobs0);
//        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(0,
                "101",
                "งานปั๊มลาก",
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child("101").setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEnvironmentPump102(){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs10 = new ArrayList<>();
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup10 = new EnvironmentCheckListForJobGroup(1,"ความพร้อมเครื่องจักร",environmentCheckListForJobs10);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup10);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs20 = new ArrayList<>();
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คระบบการทำงานของปั๊มคอนกรีต",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คน๊อตล็อคหูกระต่าย",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(2,"ตรวจเช็ตกระบอกพลังเยอร์",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คน้ำมันไฮโดรลิคและสายไฮโดรลิค",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คจารบีตามจุดหมุน",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(5,"ตรวจเช็คความหนาท่อต้องไม่ ต่ำกว่า 2.00 mm. ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup20 = new EnvironmentCheckListForJobGroup(2,"ส่วนของปั๊มคอนกรีต",environmentCheckListForJobs20);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup20);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(0,
                "102",
                "งานปั๊มบูม 32",
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child("102").setValue(environmentCheckListForJobListByTaskType);

    }



    private void generateWorkingPump102(){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ด้านกว้างพื้นที่จอด ไม่น้อยกว่า 8.30 เมตร",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ลักษณะการจอดปั๊ม,พื้นที่ไม่มีความเสี่ยง (ประจักษ์ด้วยสายตา)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"พื้นที่จอดสามารถรับน้ำหนักของตัวรถพร้อมคอนกรีตได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ชิ้นส่วนของปั้มต้องมีระยะห่างจากแหล่งจ่ายไฟ หรือเสาไฟฟ้าแรงสูง เกิน 5 ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"หน้างานมีพื้นที่ให้สามารถล้างท่อ,ปั้มได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"แนวการต่อท่อ ระยะการต่อท่อแนวราบ แนวดิ่งและความปลอดภัย",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ตั้งปั้ม โดยกางขาปั๊มออกสุด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ปั้มบูม32 >7.00ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"ปฎิบัติตามขั้นตอน WI",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"การรั่วซึมของไฮโดรลิค",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"มีการรั่วซึมท่อส่งคอนกรีต,ซิล,ประกับ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"ใช้ม้ารองท่อถูกต้องเหมาะสมตามสภาพหน้างาน",true,false,false));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

//        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
//        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ดเตรียมการกรณีคอนกรีตเหลือท้ายปั้ม",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"Slump 13±1 cm. หรือตามมาตรฐานการผลิตคอนกรีต",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"ตั้งปั้ม โดยกางขาปั้มออกสุด",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"มีการเทต่อเนื่อง  (ล่าช้าได้ไม่เกิน30นาที)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ระยะเวลาคอนรีต จากแพลนท์ - ท้ายปั้ม (ระยะเวลาไม่เกิน 2 ชม.)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ปฎิบัติตามขั้นตอน WI",false,true,true));
//        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(1,"ตรวจสอบหลังจบงาน/ขณะปฏิบัติงาน",workingCheckListForJobs0);
//        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(0,
                "102",
                "งานปั๊มบูม 32",
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child("102").setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEnvironmentPump103(){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs10 = new ArrayList<>();
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup10 = new EnvironmentCheckListForJobGroup(1,"ความพร้อมเครื่องจักร",environmentCheckListForJobs10);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup10);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs20 = new ArrayList<>();
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คระบบการทำงานของปั๊มคอนกรีต",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คน๊อตล็อคหูกระต่าย",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(2,"ตรวจเช็ตกระบอกพลังเยอร์",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คน้ำมันไฮโดรลิคและสายไฮโดรลิค",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คจารบีตามจุดหมุน",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(5,"ตรวจเช็คความหนาท่อต้องไม่ ต่ำกว่า 2.00 mm. ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup20 = new EnvironmentCheckListForJobGroup(2,"ส่วนของปั๊มคอนกรีต",environmentCheckListForJobs20);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup20);

//        List<EnvironmentCheckListForJob> environmentCheckListForJobs2 = new ArrayList<>();
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(0,"ม้ารองท่อ",true,false,true));
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(1,"ยางรองขา",true,false,true));
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(2,"ข้องอ Elbow",true,false,true));
//        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup2 = new EnvironmentCheckListForJobGroup(3,"ความพร้อมอุปกรณ์",environmentCheckListForJobs2);
//        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup2);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(0,
                "103",
                "งานปั๊มบูม 38",
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child("103").setValue(environmentCheckListForJobListByTaskType);

    }



    private void generateWorkingPump103(){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ด้านกว้างพื้นที่จอด ไม่น้อยกว่า 8.30 เมตร",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ลักษณะการจอดปั๊ม,พื้นที่ไม่มีความเสี่ยง (ประจักษ์ด้วยสายตา)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"พื้นที่จอดสามารถรับน้ำหนักของตัวรถพร้อมคอนกรีตได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ชิ้นส่วนของปั้มต้องมีระยะห่างจากแหล่งจ่ายไฟ หรือเสาไฟฟ้าแรงสูง เกิน 5 ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"หน้างานมีพื้นที่ให้สามารถล้างท่อ,ปั้มได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"แนวการต่อท่อ ระยะการต่อท่อแนวราบ แนวดิ่งและความปลอดภัย",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ตั้งปั้ม โดยกางขาปั๊มออกสุด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ปั้มบูม38 >8.30ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"ปฎิบัติตามขั้นตอน WI",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"การรั่วซึมของไฮโดรลิค",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"มีการรั่วซึมท่อส่งคอนกรีต,ซิล,ประกับ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"ใช้ม้ารองท่อถูกต้องเหมาะสมตามสภาพหน้างาน",true,false,false));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

//        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
//        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ดเตรียมการกรณีคอนกรีตเหลือท้ายปั้ม",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"Slump 13±1 cm. หรือตามมาตรฐานการผลิตคอนกรีต",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"ตั้งปั้ม โดยกางขาปั้มออกสุด",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"มีการเทต่อเนื่อง  (ล่าช้าได้ไม่เกิน30นาที)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ระยะเวลาคอนรีต จากแพลนท์ - ท้ายปั้ม (ระยะเวลาไม่เกิน 2 ชม.)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ปฎิบัติตามขั้นตอน WI",false,true,true));
//        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(1,"ตรวจสอบหลังจบงาน/ขณะปฏิบัติงาน",workingCheckListForJobs0);
//        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(0,
                "103",
                "งานปั๊มบูม 38",
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child("103").setValue(workingCheckListForJoblistByTaskType);

    }


    private void generateEnvironmentPump104(){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs10 = new ArrayList<>();
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup10 = new EnvironmentCheckListForJobGroup(1,"ความพร้อมเครื่องจักร",environmentCheckListForJobs10);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup10);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs20 = new ArrayList<>();
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คระบบการทำงานของปั๊มคอนกรีต",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คน๊อตล็อคหูกระต่าย",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(2,"ตรวจเช็ตกระบอกพลังเยอร์",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คน้ำมันไฮโดรลิคและสายไฮโดรลิค",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คจารบีตามจุดหมุน",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(5,"ตรวจเช็คความหนาท่อต้องไม่ ต่ำกว่า 2.00 mm. ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup20 = new EnvironmentCheckListForJobGroup(2,"ส่วนของปั๊มคอนกรีต",environmentCheckListForJobs20);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup20);

//        List<EnvironmentCheckListForJob> environmentCheckListForJobs2 = new ArrayList<>();
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(0,"ม้ารองท่อ",true,false,true));
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(1,"ยางรองขา",true,false,true));
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(2,"ข้องอ Elbow",true,false,true));
//        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup2 = new EnvironmentCheckListForJobGroup(3,"ความพร้อมอุปกรณ์",environmentCheckListForJobs2);
//        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup2);
        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(0,
                "104",
                "งานปั๊มบูม 42",
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child("104").setValue(environmentCheckListForJobListByTaskType);

    }



    private void generateWorkingPump104(){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ด้านกว้างพื้นที่จอด ไม่น้อยกว่า 8.30 เมตร",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ลักษณะการจอดปั๊ม,พื้นที่ไม่มีความเสี่ยง (ประจักษ์ด้วยสายตา)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"พื้นที่จอดสามารถรับน้ำหนักของตัวรถพร้อมคอนกรีตได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ชิ้นส่วนของปั้มต้องมีระยะห่างจากแหล่งจ่ายไฟ หรือเสาไฟฟ้าแรงสูง เกิน 5 ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"หน้างานมีพื้นที่ให้สามารถล้างท่อ,ปั้มได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"แนวการต่อท่อ ระยะการต่อท่อแนวราบ แนวดิ่งและความปลอดภัย",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ตั้งปั้ม โดยกางขาปั๊มออกสุด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ปั้มบูม42 >8.30ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"ปฎิบัติตามขั้นตอน WI",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"การรั่วซึมของไฮโดรลิค",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"มีการรั่วซึมท่อส่งคอนกรีต,ซิล,ประกับ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"ใช้ม้ารองท่อถูกต้องเหมาะสมตามสภาพหน้างาน",true,false,false));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

//        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
//        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ดเตรียมการกรณีคอนกรีตเหลือท้ายปั้ม",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"Slump 13±1 cm. หรือตามมาตรฐานการผลิตคอนกรีต",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"ตั้งปั้ม โดยกางขาปั้มออกสุด",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"มีการเทต่อเนื่อง  (ล่าช้าได้ไม่เกิน30นาที)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ระยะเวลาคอนรีต จากแพลนท์ - ท้ายปั้ม (ระยะเวลาไม่เกิน 2 ชม.)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ปฎิบัติตามขั้นตอน WI",false,true,true));
//        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(1,"ตรวจสอบหลังจบงาน/ขณะปฏิบัติงาน",workingCheckListForJobs0);
//        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(0,
                "104",
                "งานปั๊มบูม 42",
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child("104").setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateWorkingPump105(){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ด้านกว้างพื้นที่จอด ไม่น้อยกว่า 8.30 เมตร",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ลักษณะการจอดปั๊ม,พื้นที่ไม่มีความเสี่ยง (ประจักษ์ด้วยสายตา)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"พื้นที่จอดสามารถรับน้ำหนักของตัวรถพร้อมคอนกรีตได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ชิ้นส่วนของปั้มต้องมีระยะห่างจากแหล่งจ่ายไฟ หรือเสาไฟฟ้าแรงสูง เกิน 5 ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"หน้างานมีพื้นที่ให้สามารถล้างท่อ,ปั้มได้",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"แนวการต่อท่อ ระยะการต่อท่อแนวราบ แนวดิ่งและความปลอดภัย",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ตั้งปั้ม โดยกางขาปั๊มออกสุด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ปั้มบูม43 >8.30ม.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"ปฎิบัติตามขั้นตอน WI",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"การรั่วซึมของไฮโดรลิค",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"มีการรั่วซึมท่อส่งคอนกรีต,ซิล,ประกับ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"ใช้ม้ารองท่อถูกต้องเหมาะสมตามสภาพหน้างาน",true,false,false));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

//        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
//        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ดเตรียมการกรณีคอนกรีตเหลือท้ายปั้ม",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"Slump 13±1 cm. หรือตามมาตรฐานการผลิตคอนกรีต",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"ตั้งปั้ม โดยกางขาปั้มออกสุด",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"มีการเทต่อเนื่อง  (ล่าช้าได้ไม่เกิน30นาที)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ระยะเวลาคอนรีต จากแพลนท์ - ท้ายปั้ม (ระยะเวลาไม่เกิน 2 ชม.)",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ปฎิบัติตามขั้นตอน WI",false,true,true));
//        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(1,"ตรวจสอบหลังจบงาน/ขณะปฏิบัติงาน",workingCheckListForJobs0);
//        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(0,
                "105",
                "งานปั๊มบูม 43",
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child("105").setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEnvironmentPump105(){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs10 = new ArrayList<>();
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs10.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup10 = new EnvironmentCheckListForJobGroup(1,"ความพร้อมเครื่องจักร",environmentCheckListForJobs10);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup10);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs20 = new ArrayList<>();
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คระบบการทำงานของปั๊มคอนกรีต",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คน๊อตล็อคหูกระต่าย",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(2,"ตรวจเช็ตกระบอกพลังเยอร์",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คน้ำมันไฮโดรลิคและสายไฮโดรลิค",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คจารบีตามจุดหมุน",true,false,false));
        environmentCheckListForJobs20.add(new EnvironmentCheckListForJob(5,"ตรวจเช็คความหนาท่อต้องไม่ ต่ำกว่า 2.00 mm. ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup20 = new EnvironmentCheckListForJobGroup(2,"ส่วนของปั๊มคอนกรีต",environmentCheckListForJobs20);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup20);

//        List<EnvironmentCheckListForJob> environmentCheckListForJobs2 = new ArrayList<>();
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(0,"ม้ารองท่อ",true,false,true));
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(1,"ยางรองขา",true,false,true));
//        environmentCheckListForJobs2.add(new EnvironmentCheckListForJob(2,"ข้องอ Elbow",true,false,true));
//        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup2 = new EnvironmentCheckListForJobGroup(3,"ความพร้อมอุปกรณ์",environmentCheckListForJobs2);
//        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup2);
        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(0,
                "105",
                "งานปั๊มบูม 43",
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child("105").setValue(environmentCheckListForJobListByTaskType);

    }

    private void generateEnvironmentPolish(){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คจุดอันจารบี",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจน้ำมันไฮดรอลิค",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจน้ำมันเครื่อง",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจสอบลมยาง",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจสอบระบบไฟ(หน้าจอ)",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(5,"ตรวจการทำงานของวาย",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(6,"ตรวจการขึ้น-ลง ของผานปาดและโกย",true,false,true));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);


        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(0,
                "203",
                "งานปาดลเซอร์",
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child("203").setValue(environmentCheckListForJobListByTaskType);

    }
    private void generateWorkingPump1(){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ด้านกว้างพื้นที่จอด ไม่น้อยกว่า 8.30 เมตร",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"ลักษณะการจอดปั๊ม,พื้นที่ไม่มีความเสี่ยง (ประจักษ์ด้วยสายตา)",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"พื้นที่จอดสามารถรับน้ำหนักของตัวรถพร้อมคอนกรีตได้",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"สิ่งแวดล้อมรอบๆบริเวณรถปั๊ม ไม่พังทลายหรือล้มเมื่อมีแรงลมเกิดขึ้น",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ชิ้นส่วนของปั้มไม่ใกล้กับแหล่งจ่ายไฟ หรือเสาไฟฟ้าแรงสูง เกิน 2.50ม.",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ไม่ใกล้กับแหล่งเก็บวัตถุไวไฟ หรืออื่นๆที่ทำให้เกิดอัคคีภัยได้",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(6,"การสัญจรของรถปูนสำหรับเข้าท้ายปั๊มสะดวก",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(7,"มีพื้นที่จอดรอคิวรถคอนกรีต เพื่องานเทต่อเนื่อง",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(8,"หน้างานมีพื้นที่ให้สามารถล้างท่อ,ปั้มได้",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(9,"แนวการต่อท่อ ระยะการต่อท่อแนวราบ แนวดิ่งและความปลอดภัย",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(10,"การรั่วซึมของไฮโดรลิค",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(11,"มีการรั่วซึมท่อส่งคอนกรีต,ซิล,ประกับ",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(12,"ใช้ม้ารองท่อถูกต้องเหมาะสมตามสภาพหน้างาน",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(13,"ปฏิบัติงานด้วยความปลอดภัย",true,false,false));
        workingCheckListForJobs.add(new WorkingCheckListForJob(14,"พฤติกรรมพนักงงานเป็นไปตามกฏระเบียบ",true,false,false));
        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(0,
                "101",
                "งานปั๊มลาก",
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child("101").setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateWorkingPolish(){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"เตรียมการกรณีคอนกรีตเหลือท้ายปั้ม",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"Slump 13±1 cm. หรือตามมาตรฐานการผลิตคอนกรีต",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"พตั้งปั้ม โดยกางขาปั้มออกสุด",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"มีการเทต่อเนื่อง  (ล่าช้าได้ไม่เกิน30นาที)",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ระยะเวลาคอนรีต จากแพลนท์ - ท้ายปั้ม (ระยะเวลาไม่เกิน 2 ชม.)",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ปฎิบัติตามขั้นตอน WI",false,true,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(1,
                "203",
                "งานปาด",
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child("203").setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateTaskGourp(){
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        list.add("ปั๊มลาก");
        list.add("ปั๊ม บูม 32 เมตร");
        list.add("ปั๊มบูม 38 เมตร");
        list.add("ปั๊มบูม 42 เมตร");
        list.add("ปั๊มบูม 43 เมตร");


        list1.add("ปาดกล่อง");
        list1.add("ปาดสามเหลี่ยม");
        list1.add("ปาด Laser Screed");
        list1.add("ปาด Truss Screed");
        list1.add("ปาด Magic Screed");
//        list1.add("ปาด Laser Screed/จี้วายเบรอเตอร์");

        list2.add("ขัดหยาบ");
        list2.add("ขัดเรียบ");
        list2.add("ขัดมัน");
        list2.add("ขัด burnished floor");
        list2.add("ขัด Floor Hardener");

        list3.add("วางเหล็ก,มัดเหล็ก,เข้าแบบ");
        list3.add("ติดตั้ง Armour Joint");
        list3.add("ล้างพื้น ลงแว็กซ์");
        list3.add("Saw Cut Joint");
        list3.add("Polyurethane Sealant");
        list3.add("หยอดยางมะตอย");
        list3.add("epoxy injection และ epoxy patching");
        list3.add("ตัดสกัด ซ่อมพื้นคอนกรีต กรณีสกัดด้วยเครื่องลม");
        list3.add("ตัดสกัด ซ่อมพื้นคอนกรีต กรณีสกัดด้วยสกัดไฟฟ้า");
        list3.add("ทำงานบ้าน");
        //ส่วนซ่อม
        list3.add("ซ่อมปาดกล่อง");
        list3.add("ซ่อมปาดสามเหลี่ยม");
        list3.add("ซ่อมปาด Laser Screed");
        list3.add("ซ่อมปาด Truss Screed");
        list3.add("ซ่อมปาด Magic Screed");
        list3.add("ซ่อมขัดหยาบ");
        list3.add("ซ่อมขัดเรียบ");
        list3.add("ซ่อมขัดมัน");
        list3.add("ซ่อมขัด burnished floor");
        list3.add("ซ่อมขัด Floor Hardener");

        list4.add("งานส่งเอกสาร");
        list4.add("งานรับเอกสาร");
        list4.add("งานอบรม");
        list4.add("ขับ 6 ล้อ");
        list4.add("ขับรถเฮี๊ยบ");
        List<TaskTypeMaster> taskTypeMasters = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            taskTypeMasters.add(new TaskTypeMaster("10"+(i+1),list.get(i)));
        }
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_SETTING).child(AppConstant.DB_MASTER_TASKTYPELIST).child("1").setValue(new TaskGroupMaster("1","งานปั๊ม",taskTypeMasters));

        List<TaskTypeMaster> taskTypeMasters2 = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            taskTypeMasters2.add(new TaskTypeMaster("20"+(i+1),list1.get(i)));
        }
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_SETTING).child(AppConstant.DB_MASTER_TASKTYPELIST).child("2").setValue(new TaskGroupMaster("2","งานปาด",taskTypeMasters2));

        List<TaskTypeMaster> taskTypeMasters3 = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            taskTypeMasters3.add(new TaskTypeMaster("30"+(i+1),list2.get(i)));
        }
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_SETTING).child(AppConstant.DB_MASTER_TASKTYPELIST).child("3").setValue(new TaskGroupMaster("3","งานขัด",taskTypeMasters3));

        List<TaskTypeMaster> taskTypeMasters4 = new ArrayList<>();
        for (int i = 0; i < list3.size(); i++) {
            taskTypeMasters4.add(new TaskTypeMaster("40"+(i+1),list3.get(i)));
        }
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_SETTING).child(AppConstant.DB_MASTER_TASKTYPELIST).child("4").setValue(new TaskGroupMaster("4","งานอื่นๆ",taskTypeMasters4));

        List<TaskTypeMaster> taskTypeMasters5 = new ArrayList<>();
        for (int i = 0; i < list4.size(); i++) {
            taskTypeMasters5.add(new TaskTypeMaster("50"+(i+1),list4.get(i)));
        }
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_SETTING).child(AppConstant.DB_MASTER_TASKTYPELIST).child("5").setValue(new TaskGroupMaster("5","งานเอกสาร",taskTypeMasters5));

    }

    private void generateEquipmentPolish(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่องขัด (เดินตาม)",4));
        listEQ.add(new EquipmentForJob(1,"เครื่องขัด (นั่งขับ)",0));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"จานเครื่องขัด",2));
        listEQ0.add(new EquipmentForJob(1,"จานเครื่องขัด (สำรอง)",2));
        listEQ0.add(new EquipmentForJob(2,"ใบขัด (สำรอง)",4));
        listEQ0.add(new EquipmentForJob(3,"ไม้ รีดน้ำ",2));
        listEQ0.add(new EquipmentForJob(4,"ไม้ โรว",1));
        listEQ0.add(new EquipmentForJob(5,"เกรียงพลาสติก",6));
        listEQ0.add(new EquipmentForJob(6,"เกรียงโป๊ว (แซะ) 5 นิ้ว",2));
        listEQ0.add(new EquipmentForJob(7,"ไม้กวาด อ่อน",2));
        listEQ0.add(new EquipmentForJob(8,"กล่องลากระดับน้ำยาว 1.5 ม.",2));
        listEQ0.add(new EquipmentForJob(9,"กระป๋องใส่น้ำ",20));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));

        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"สายพาน #35",4));
        listEQ2.add(new EquipmentForJob(1,"ข้อต่อโซ่",4));
        listEQ2.add(new EquipmentForJob(2,"สปอตไลท์",2));
        listEQ2.add(new EquipmentForJob(3,"หัวเทียน 5 HP",2));
        listEQ2.add(new EquipmentForJob(4,"หัวเทียน 9 HP",2));
        listEQ2.add(new EquipmentForJob(5,"สายไฟ 50 ม.",2));
        listEQ2.add(new EquipmentForJob(6,"ข้อต่อไฟ 3 ทาง",1));
        listEQ2.add(new EquipmentForJob(7,"ถังน้ำมันเบนซิน (30 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(8,"น้ำมันเครื่อง (5 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(9,"ฝาครอบสายพาน",4));
        listEQ2.add(new EquipmentForJob(10,"กระติกน้ำดื่ม+ถังน้ำ",1));

        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentCut201(int rowNo,String taskTypeCode,String taskType,String desc){

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"สามเหลี่ยม",4));
        listEQ0.add(new EquipmentForJob(1,"ไม้โกย",8));
        listEQ0.add(new EquipmentForJob(2,"กระป๋องใส่น้ำ",5));
        listEQ0.add(new EquipmentForJob(3,"ผ้าไมโครไฟเบอร์",3));
        listEQ0.add(new EquipmentForJob(4,"เกรียง (พลาสติก)",4));
        listEQ0.add(new EquipmentForJob(5,"ไชแนลโฟลด",1));
        listEQ0.add(new EquipmentForJob(6,"กล่องลากระดับน้ำ 1.5 ม.",2));
        listEQ0.add(new EquipmentForJob(7,"ตัวกดหิน",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"ประแจหกเหลี่ยม #8",1));
        listEQ1.add(new EquipmentForJob(15,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(16,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(18,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(19,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(20,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(21,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(22,"กระเป๋าเครื่องมือ",1));


        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"สปอตไลท์",2));
        listEQ2.add(new EquipmentForJob(1,"หัวเทียน 5 HP",2));
        listEQ2.add(new EquipmentForJob(2,"หัวเทียน 9 HP",2));
        listEQ2.add(new EquipmentForJob(3,"สายไฟ 50 ม.",2));
        listEQ2.add(new EquipmentForJob(4,"ข้อต่อไฟ 3 ทาง",1));
        listEQ2.add(new EquipmentForJob(5,"ถังน้ำมันเบนซิน (30 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(6,"น้ำมันเครื่อง (5 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(7,"น้ำมันไฮดรอลิค#68 (20 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(8,"จาระบี #2 (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(9,"จาระบี จรเข้ (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(10,"ที่อัด จาระบี ",2));
        listEQ2.add(new EquipmentForJob(11,"ถ่านไฟฉาย 1A",8));
        listEQ2.add(new EquipmentForJob(12,"ถ่านไฟฉาย 2A",4));
        listEQ2.add(new EquipmentForJob(13,"กระติกน้ำดื่ม+ถังน้ำ",1));


        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentCut202(int rowNo,String taskTypeCode,String taskType,String desc){

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"สามเหลี่ยม",4));
        listEQ0.add(new EquipmentForJob(1,"ไม้โกย",8));
        listEQ0.add(new EquipmentForJob(2,"กระป๋องใส่น้ำ",5));
        listEQ0.add(new EquipmentForJob(3,"ผ้าไมโครไฟเบอร์",3));
        listEQ0.add(new EquipmentForJob(4,"เกรียง (พลาสติก)",4));
        listEQ0.add(new EquipmentForJob(5,"ไม้สต๊าฟ",1));
        listEQ0.add(new EquipmentForJob(6,"ไชแนลโฟลด",1));
        listEQ0.add(new EquipmentForJob(7,"กล่องลากระดับน้ำ 1.5 ม.",2));
        listEQ0.add(new EquipmentForJob(8,"ตัวกดหิน",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"ประแจหกเหลี่ยม #8",1));
        listEQ1.add(new EquipmentForJob(15,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(16,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(18,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(19,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(20,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(21,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(22,"กระเป๋าเครื่องมือ",1));


        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"สปอตไลท์",2));
        listEQ2.add(new EquipmentForJob(1,"หัวเทียน 5 HP",2));
        listEQ2.add(new EquipmentForJob(2,"หัวเทียน 9 HP",2));
        listEQ2.add(new EquipmentForJob(3,"สายไฟ 50 ม.",2));
        listEQ2.add(new EquipmentForJob(4,"ข้อต่อไฟ 3 ทาง",1));
        listEQ2.add(new EquipmentForJob(5,"ถังน้ำมันเบนซิน (30 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(6,"น้ำมันเครื่อง (5 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(7,"น้ำมันไฮดรอลิค#68 (20 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(8,"จาระบี #2 (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(9,"จาระบี จรเข้ (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(10,"ที่อัด จาระบี ",2));
        listEQ2.add(new EquipmentForJob(11,"ถ่านไฟฉาย 1A",8));
        listEQ2.add(new EquipmentForJob(12,"ถ่านไฟฉาย 2A",4));
        listEQ2.add(new EquipmentForJob(13,"กระติกน้ำดื่ม+ถังน้ำ",1));


        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentCut203(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"รถปาด  S485",1));
        listEQ.add(new EquipmentForJob(1,"รถปาด  Copper Head 3",1));
        listEQ.add(new EquipmentForJob(2,"รถโกย Power Rack",1));
        listEQ.add(new EquipmentForJob(3,"เครื่องจี้วายใหญ่",1));
        listEQ.add(new EquipmentForJob(4,"เครื่องจี้วายเล็ก",1));
        listEQ.add(new EquipmentForJob(5,"ปั้มน้ำล้างอุปกรณ์",1));

        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"สามเหลี่ยม",4));
        listEQ0.add(new EquipmentForJob(1,"ไม้โกย",8));
        listEQ0.add(new EquipmentForJob(2,"กระป๋องใส่น้ำ",5));
        listEQ0.add(new EquipmentForJob(2,"ผ้าไมโครไฟเบอร์",3));
        listEQ0.add(new EquipmentForJob(4,"เกรียง (พลาสติก)",4));
        listEQ0.add(new EquipmentForJob(5,"ไม้สต๊าฟ",1));
        listEQ0.add(new EquipmentForJob(6,"กล้องเลเซอร์",1));
        listEQ0.add(new EquipmentForJob(7,"ขาตั้งกล้องเลเซอร์ (3 ขา)",1));
        listEQ0.add(new EquipmentForJob(8,"ขาตั้งกล้องเลเซอร์ (ติดเสา)",1));
        listEQ0.add(new EquipmentForJob(9,"ตัวรับสัญญาณ กล้องเลเซอร์",1));
        listEQ0.add(new EquipmentForJob(10,"ตัวรับสัญญาณ รถปาด (คู่)",1));
        listEQ0.add(new EquipmentForJob(11,"ตัวรับสัญญาณ รถโกย (คู่)",1));
        listEQ0.add(new EquipmentForJob(12,"ไชแนลโฟลด",1));
        listEQ0.add(new EquipmentForJob(13,"กล่องลากระดับน้ำ 1.5 ม.",2));
        listEQ0.add(new EquipmentForJob(14,"ตัวกดหิน",1));
        listEQ0.add(new EquipmentForJob(15,"สายไฮดรอลิคหัว #14 ยาว 60 เซน",1));
        listEQ0.add(new EquipmentForJob(16,"สายไฮดรอลิคหัว #14 ยาว 1 เมตร 40 เซน",1));
        listEQ0.add(new EquipmentForJob(17,"สายไฮดรอลิคหัว #19 ยาว 1 เมตร 80 เซน",1));
        listEQ0.add(new EquipmentForJob(18,"สายไฮดรอลิคหัว #24 ยาว 1 เมตร 10 เซน",1));
        listEQ0.add(new EquipmentForJob(19,"สายไฮดรอลิคหัว #24 ยาว 1 เมตร 90 เซน",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"ประแจหกเหลี่ยม #8",1));
        listEQ1.add(new EquipmentForJob(15,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(16,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(18,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(19,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(20,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(21,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(22,"กระเป๋าเครื่องมือ",1));


        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"สปอตไลท์",2));
        listEQ2.add(new EquipmentForJob(1,"หัวเทียน 5 HP",2));
        listEQ2.add(new EquipmentForJob(2,"หัวเทียน 9 HP",2));
        listEQ2.add(new EquipmentForJob(3,"สายไฟ 50 ม.",2));
        listEQ2.add(new EquipmentForJob(4,"ข้อต่อไฟ 3 ทาง",1));
        listEQ2.add(new EquipmentForJob(5,"ถังน้ำมันเบนซิน (30 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(6,"น้ำมันเครื่อง (5 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(7,"น้ำมันไฮดรอลิค#68 (20 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(8,"จาระบี #2 (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(9,"จาระบี จรเข้ (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(10,"ที่อัด จาระบี ",2));
        listEQ2.add(new EquipmentForJob(11,"ถ่านไฟฉาย 1A",8));
        listEQ2.add(new EquipmentForJob(12,"ถ่านไฟฉาย 2A",4));
        listEQ2.add(new EquipmentForJob(13,"กระติกน้ำดื่ม+ถังน้ำ",1));


        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentCut204(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่อง Truss Screed",1));
        listEQ.add(new EquipmentForJob(1,"เครื่องจี้วายใหญ่",1));
        listEQ.add(new EquipmentForJob(2,"เครื่องจี้วายเล็ก",1));
        listEQ.add(new EquipmentForJob(3,"ปั้มน้ำล้างอุปกรณ์",1));

        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"สามเหลี่ยม",4));
        listEQ0.add(new EquipmentForJob(1,"ไม้โกย",8));
        listEQ0.add(new EquipmentForJob(2,"กระป๋องใส่น้ำ",5));
        listEQ0.add(new EquipmentForJob(3,"ผ้าไมโครไฟเบอร์",3));
        listEQ0.add(new EquipmentForJob(4,"เกรียง (พลาสติก)",4));
        listEQ0.add(new EquipmentForJob(5,"ไม้สต๊าฟ",1));
        listEQ0.add(new EquipmentForJob(6,"ไชแนลโฟลด",1));
        listEQ0.add(new EquipmentForJob(7,"กล่องลากระดับน้ำ 1.5 ม.",2));
        listEQ0.add(new EquipmentForJob(8,"ตัวกดหิน",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"ประแจหกเหลี่ยม #8",1));
        listEQ1.add(new EquipmentForJob(15,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(16,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(18,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(19,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(20,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(21,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(22,"กระเป๋าเครื่องมือ",1));


        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"สปอตไลท์",2));
        listEQ2.add(new EquipmentForJob(1,"หัวเทียน 5 HP",2));
        listEQ2.add(new EquipmentForJob(2,"หัวเทียน 9 HP",2));
        listEQ2.add(new EquipmentForJob(3,"สายไฟ 50 ม.",2));
        listEQ2.add(new EquipmentForJob(4,"ข้อต่อไฟ 3 ทาง",1));
        listEQ2.add(new EquipmentForJob(5,"ถังน้ำมันเบนซิน (30 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(6,"น้ำมันเครื่อง (5 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(7,"น้ำมันไฮดรอลิค#68 (20 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(8,"จาระบี #2 (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(9,"จาระบี จรเข้ (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(10,"ที่อัด จาระบี ",2));
        listEQ2.add(new EquipmentForJob(11,"ถ่านไฟฉาย 1A",8));
        listEQ2.add(new EquipmentForJob(12,"ถ่านไฟฉาย 2A",4));
        listEQ2.add(new EquipmentForJob(13,"กระติกน้ำดื่ม+ถังน้ำ",1));


        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentCut205(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่อง Magic Screed",1));
        listEQ.add(new EquipmentForJob(1,"เครื่องจี้วาย Power rack ใหญ่",1));
        listEQ.add(new EquipmentForJob(2,"เครื่องจี้วาย Power rack เล็ก",1));
        listEQ.add(new EquipmentForJob(3,"ปั้มน้ำล้างอุปกรณ์ใหญ่",1));
        listEQ.add(new EquipmentForJob(4,"ปั้มน้ำล้างอุปกรณ์เล็ก",1));

        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"สามเหลี่ยม",4));
        listEQ0.add(new EquipmentForJob(1,"ไม้โกย",8));
        listEQ0.add(new EquipmentForJob(2,"กระป๋องใส่น้ำ",5));
        listEQ0.add(new EquipmentForJob(3,"ผ้าไมโครไฟเบอร์",3));
        listEQ0.add(new EquipmentForJob(4,"เกรียง (พลาสติก)",4));
        listEQ0.add(new EquipmentForJob(5,"ไม้สต๊าฟ",1));
        listEQ0.add(new EquipmentForJob(6,"ไชแนลโฟลด",1));
        listEQ0.add(new EquipmentForJob(7,"กล่องลากระดับน้ำ 1.5 ม.",2));
        listEQ0.add(new EquipmentForJob(8,"ตัวกดหิน",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"ประแจหกเหลี่ยม #8",1));
        listEQ1.add(new EquipmentForJob(15,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(16,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(18,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(19,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(20,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(21,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(22,"กระเป๋าเครื่องมือ",1));


        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"สปอตไลท์",2));
        listEQ2.add(new EquipmentForJob(1,"หัวเทียน 5 HP",2));
        listEQ2.add(new EquipmentForJob(2,"หัวเทียน 9 HP",2));
        listEQ2.add(new EquipmentForJob(3,"สายไฟ 50 ม.",2));
        listEQ2.add(new EquipmentForJob(4,"ข้อต่อไฟ 3 ทาง",1));
        listEQ2.add(new EquipmentForJob(5,"ถังน้ำมันเบนซิน (30 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(6,"น้ำมันเครื่อง (5 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(7,"น้ำมันไฮดรอลิค#68 (20 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(8,"จาระบี #2 (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(9,"จาระบี จรเข้ (10 กก.)",1));
        listEQ2.add(new EquipmentForJob(10,"ที่อัด จาระบี ",2));
        listEQ2.add(new EquipmentForJob(11,"ถ่านไฟฉาย 1A",8));
        listEQ2.add(new EquipmentForJob(12,"ถ่านไฟฉาย 2A",4));
        listEQ2.add(new EquipmentForJob(13,"กระติกน้ำดื่ม+ถังน้ำ",1));


        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentPolish304(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่อง Polishing",2));
        listEQ.add(new EquipmentForJob(1,"สคับเบอร์",1));
        listEQ.add(new EquipmentForJob(2,"โพเพน",1));
        listEQ.add(new EquipmentForJob(3,"เครื่องพ่น",1));
        listEQ.add(new EquipmentForJob(4,"หินเจียร",2));

        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"ไม้รีดน้ำ",5));
        listEQ0.add(new EquipmentForJob(1,"สายยาง         (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(2,"ผ้าไมโครไฟเบอร์",5));
        listEQ0.add(new EquipmentForJob(3,"ที่ตักผง ",2));
        listEQ0.add(new EquipmentForJob(4,"ไม้ม๊อบ",5));
        listEQ0.add(new EquipmentForJob(5,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(6,"สายไฟ 3 เฟส  (50 ม.)",2));
        listEQ0.add(new EquipmentForJob(7,"สายไฟ 1 เฟส  (50 ม.)",3));
        listEQ0.add(new EquipmentForJob(8,"สามทาง 1 เฟส",1));
        listEQ0.add(new EquipmentForJob(9,"สามทาง 3 เฟส",1));
        listEQ0.add(new EquipmentForJob(10,"รถเข็นน้ำ",1));
        listEQ0.add(new EquipmentForJob(11,"ใบ Polishing #30 (12 ใบ)",4));
        listEQ0.add(new EquipmentForJob(12,"ใบ Polishing #50 (12 ใบ)",4));
        listEQ0.add(new EquipmentForJob(13,"ใบ Polishing #100 (12 ใบ)",4));
        listEQ0.add(new EquipmentForJob(14,"ใบ Polishing #200 (12 ใบ)",4));
        listEQ0.add(new EquipmentForJob(15,"ใบ Polishing #400 (12 ใบ)",4));
        listEQ0.add(new EquipmentForJob(16,"ใบ Polishing #800 (12 ใบ)",4));
        listEQ0.add(new EquipmentForJob(17,"ใบ Polishing #1500 (12 ใบ)",4));
        listEQ0.add(new EquipmentForJob(18,"จานเหล็กตีนตุ๊กแก (12 ใบ)",2));
        listEQ0.add(new EquipmentForJob(19,"จานเหล็กตีนตุ๊กแกหินเจียร (12 ใบ)",2));
        listEQ0.add(new EquipmentForJob(20,"ใบโพเพน #800",2));
        listEQ0.add(new EquipmentForJob(21,"ใบโพเพน #1800",2));

        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 5 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));


        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"ลิควิด ฮาร์ดเดนเนอร์ (20 ลิตร)",2));
        listEQ2.add(new EquipmentForJob(1,"แวกซ์น้ำ (20 ลิตร)",2));
        listEQ2.add(new EquipmentForJob(2,"น้ำมันดันฝุ่น (5 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(3,"NS 50",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"เคมี",listEQ2);

        List<EquipmentForJob> listEQ3 = new ArrayList<>();
        listEQ3.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup4 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ3);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        equipmentForJobGroups.add(equipmentForJobGroup4);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEnvironmentPolish301_302_303(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);



        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจสอบโซ่ปรับใบ สำหรับเครื่องเดินตาม",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจสอบความแข็งแรงโครงขัด(เครื่องเดินตาม)",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจสอบใบขัด",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจสอบแบตเตอรี่(เครื่องนั่งขับ)",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(5,"ตรวจสอบถาดขัดหน้าปูน",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(6,"ตรวจสอบคันเร่ง,ตัวเร่งเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(7,"ตรวจสอบแกนบังคับทิศทาง(เครื่องนั่งขับ)",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);


    }





    private void generateEnvironmentPolish304(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจสอบระบบไฟทดลองเปิดเครื่อง",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจสอบสายไฟไม่ชำรุด",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจสอบตัวยึดใบอยู่ในสภาพดี",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจตัวปรับรอบเครื่อง",true,false,true));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);

// environment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ต้องไม่มีสิ่งกีดขวางการทำงาน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ต้องไม่มีปลั๊กไฟที่พื้น",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"พื้นที่ทำงานต้องไม่มีกระแสไฟรั่ว",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ต้องกั้นพื้นที่ ขณะทำงาน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"ต้องมีการ Protection วัสดุหรือสิ่งของที่อาจได้รับความเสียหายหรือฝุ่นเกาะน้ำกระเด็น",true,false,false));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ต้องมีความมันเงา",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"พื้นต้องสะอาดไม่มีคราบโคลน",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"ต้องไม่ให้มีรอยซ่อนมากเกินไป",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"ความเงาโดยรวม",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"จความเงาสม่ำเสมอ",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ลอยด่าง",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(6,"ความสะอาด",false,true,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(1,"ตรวจสอบหลังจบงาน/ขณะปฏิบัติงาน",workingCheckListForJobs);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEnvironmentPolish305_306(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจสอบโซ่ปรับใบ สำหรับเครื่องเดินตาม",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจสอบความแข็งแรงโครงขัด(เครื่องเดินตาม)",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจสอบใบขัด",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจสอบแบตเตอรี่(เครื่องนั่งขับ)",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(5,"ตรวจสอบถาดขัดหน้าปูน",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(6,"ตรวจสอบคันเร่ง,ตัวเร่งเครื่อง",true,false,true));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(7,"ตรวจสอบแกนบังคับทิศทาง(เครื่องนั่งขับ)",true,false,true));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);
//environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"คอนกรีตที่ใช้มีกำลังอัดตามข้อกำหนดสำหรับงานผิวคอนกรีตต่างๆที่ต้องการ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ค่ายุบตัวของคอนกรีต (Slump) เป็นไปตามาตรฐานที่กำหนด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"Slump 13±1cm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ปริมาณและอัตราการเทในแต่ล่ะครั้งไม่มากเกินไป",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"การทำหลังคา ทำงานในร่ม",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"การเตรียมแสงสว่างเพียงพอต่อการทำงาน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"การเตรียมแผนรับมือเมื่อเกิดฝนตก กรณีงานกลางแจ้ง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"จุดใช้น้ำอยู่ใกล้พื้นที่ทำงาน ไม่เกิน20เมตร",true,false,false));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ปริมาณและอัตราการเทในแต่ล่ะครั้งเต็มพื้นที่แบบ",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"ระดับFinishing",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"ความเงาโดยรวม(ประจักษด้วยสาย)",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"ความเงาขอบ(ประจักษด้วยสาย)",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ความเรียบขอบ(ประจักษด้วยสาย)",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ความสะอาดบริเวณทำงาน",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(6,"การเตรียมแผนรับมือเมื่อเกิดฝนตก กรณีงานกลางแจ้ง",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(7,"ระดับขอบ",false,true,true));
        workingCheckListForJobs.add(new WorkingCheckListForJob(8,"โคจอยต์",false,true,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(1,"ตรวจสอบหลังจบงาน/ขณะปฏิบัติงาน",workingCheckListForJobs);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEnvironmentCut205(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจสอบสภาพเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจสอบผานปาดไม่ชำรุดคด งอ",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจสอบน้ำมันเครื่อง",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);

    }

    private void generateEnvironmentCut203(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();


        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);


        List<EnvironmentCheckListForJob> environmentCheckListForJobs1 = new ArrayList<>();
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คจุดอัดจารบี",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(1,"ตรวจน้ำมันไฮดรอลิค",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(2,"ตรวจน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(3,"ตรวจสอบลมยาง",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(4,"ตรวจสอบระบบไฟ(หน้าจอ)",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(5,"ตรวจการทำงานของวาย",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(6,"ตรวจการขึ้น-ลง ของผานปาดและโกย",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(7,"ตรวจสายตัวรับสัญญาณ",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(8,"ตรวจตัวรับสัญญานที่เครื่องปาดและโกย",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(9,"ตรวจกล้องปล่อยสัญญาณ",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(10,"ตรวจแบตเตอรี่กล้องปล่อยสัญญาณ",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(11,"ตรวจตัวรับสัญญาณที่ไม้สต๊าฟ",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(12,"ตรวจแบตรี่ตัวรัญญาณที่ไม้สต๊าฟ",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(13,"ตรวจสภาพขาตั้งกล้อง",true,false,false));
        environmentCheckListForJobs1.add(new EnvironmentCheckListForJob(14,"ตรวจสภาพไม้สต๊าฟ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup1 = new EnvironmentCheckListForJobGroup(1,"เครื่องจักร",environmentCheckListForJobs1);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup1);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);

    }

    private void generateEnvironmentCut204(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();

        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คจุดอัดจารบี",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจสอบน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจสอบสภาพโครงไม่คด ไม่งอ ไม่แอ่น",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจสายสลิงอยู่ในสภาพดีไม่ขาดชำรุด",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจสภาพรอกมือหมุน",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(5,"ตรวจรอกมือหมุน",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(6,"ตรวจตัวปรับระดับ",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(7,"ใช้งานได้",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(8,"ตรวจสภาพโครง Truss Screed",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(9,"ตรวจสายสลิง",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);
//environtment
//        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
//        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ระดับหลังไกด์ลาย",true,false,false));
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ขนาดเหล็กไกด์ลาย (เหล็กฉากขนาดไม่น้อยกว่า 1\"x1\" หนา4mm.)",true,false,false));
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"ขนาดเหล็กขาไกด์",true,false,false));
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ระยะช่วงไกด์ (ไม่เกิน6.00m.)",true,false,false));
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"ค่ายุบตัวของคอนกรีต (Slump) เป็นไปตามมาตรฐานที่กำหนด",true,false,false));
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"Slump 13±1cm",true,false,false));
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"การป้องกันอุณหภูมิจากลม (Wind Break)",true,false,false));
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"การทำหลังคา (ทำงานในร่ม)",true,false,false));
//        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"การออกแบบการเทเป็นไปตามทิศทางที่ถูกต้อง",true,false,false));
//        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
//        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);
//
//        List<WorkingCheckListForJob> workingCheckListForJobs = new ArrayList<>();
//        workingCheckListForJobs.add(new WorkingCheckListForJob(0,"ปฏิบัติงานตาม WI",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(1,"ปฏิบัติงานด้วยความปลอดภัย",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(2,"พฤติกรรมพนักงานเป็นไปตามกฎระเบียบ",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(3,"ระดับจุดอ้างอิง",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(4,"ระดับขอบ",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(5,"ค่าระดับเมื่อปาดเสร็จ",false,true,true));
//        workingCheckListForJobs.add(new WorkingCheckListForJob(6,"ความสะอาดบริเวณทำงาน",false,true,true));
//        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(1,"ตรวจสอบหลังจบงาน/ขณะปฏิบัติงาน",workingCheckListForJobs);
//        workingCheckListForJobGroups.add(workingCheckListForJobGroup);
//
//        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
//                taskTypeCode,
//                taskType,
//                "",
//                workingCheckListForJobGroups);
//        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEquipmentOther401(int rowNo,String taskTypeCode,String taskType,String desc){//เข้าแบบ
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"ตู้เชื่อมไฟฟ้า",1));
        listEQ.add(new EquipmentForJob(1,"หินเจียร 4 นิ้ว",1));
        listEQ.add(new EquipmentForJob(2,"ไฟเบอร์ตัด 14 นิ้ว",1));
        listEQ.add(new EquipmentForJob(3,"สว่านโรตารี่",1));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"กล้องส่องระดับ",1));
        listEQ0.add(new EquipmentForJob(1,"ขาตั้งกล้องส่องระดับ",1));
        listEQ0.add(new EquipmentForJob(2,"ไม้วัดระดับ",1));
        listEQ0.add(new EquipmentForJob(3,"กล่องลากระดับน้ำยาว 1.5 ม.",1));
        listEQ0.add(new EquipmentForJob(4,"สายเอ็น",1));
        listEQ0.add(new EquipmentForJob(5,"ฉากเหล็ก",1));
        listEQ0.add(new EquipmentForJob(6,"เต้าตีเส้น",1));
        listEQ0.add(new EquipmentForJob(7,"สายไฟ 1 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(8,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(9,"สายไฟ 3 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(10,"สามทาง 1 เฟส",1));
        listEQ0.add(new EquipmentForJob(11,"สามทาง 3 เฟส",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentOtherMobFloor(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่องม๊อป",2));
        listEQ.add(new EquipmentForJob(2,"โพเพน",1));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"Wax",4));
        listEQ0.add(new EquipmentForJob(1,"สก๊อตไบร์ท",5));
        listEQ0.add(new EquipmentForJob(2,"น้ำยาดันฝุ่น",2));
        listEQ0.add(new EquipmentForJob(3,"ใบขัดโพเพน",2));
        listEQ0.add(new EquipmentForJob(4,"ผ้าไมโครไฟเบอร์",2));
        listEQ0.add(new EquipmentForJob(5,"สายยาง (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(6,"กระป๋องใส่น้ำ",10));
        listEQ0.add(new EquipmentForJob(7,"สายไฟ 1 เฟส  (50 ม.)",4));
        listEQ0.add(new EquipmentForJob(8,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(9,"สายไฟ 3 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(10,"สามทาง 1 เฟส",1));
        listEQ0.add(new EquipmentForJob(11,"สามทาง 3 เฟส",1));
        listEQ0.add(new EquipmentForJob(12,"ไม้กวาด",2));
        listEQ0.add(new EquipmentForJob(13,"ไม้รีดน้ำ",3));
        listEQ0.add(new EquipmentForJob(14,"น้ำยาล้างพื้น/ซันไลต์/ผงซักฟอก",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }
    private void generateEquipmentOtherjointCut(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่องตัด 18 นิ้ว",2));
        listEQ.add(new EquipmentForJob(1,"โบลเวอร์",1));
        listEQ.add(new EquipmentForJob(2,"หินเจียรเล็ก",2));
        listEQ.add(new EquipmentForJob(3,"หินเจียรใหญ่",2));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"ลูกหมู",1));
        listEQ0.add(new EquipmentForJob(1,"หมึกเต๊า",2));
        listEQ0.add(new EquipmentForJob(2,"สายยาง(100 ม.)",1));
        listEQ0.add(new EquipmentForJob(3,"กระป๋องใส่น้ำ",10));
        listEQ0.add(new EquipmentForJob(4,"ผ้าไมโครไฟเบอร์",3));
        listEQ0.add(new EquipmentForJob(5,"ใบตัด 18 นิ้ว (10 มิล)",3));
        listEQ0.add(new EquipmentForJob(6,"ใบตัดหินเจียร",3));
        listEQ0.add(new EquipmentForJob(7,"สายไฟ 1 เฟส  (50 ม.)",4));
        listEQ0.add(new EquipmentForJob(8,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(9,"สายไฟ 3 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(10,"สามทาง 1 เฟส",1));
        listEQ0.add(new EquipmentForJob(11,"สามทาง 3 เฟส",1));
        listEQ0.add(new EquipmentForJob(12,"ไม้กวาด",2));
        listEQ0.add(new EquipmentForJob(13,"ไม้รีดน้ำ",3));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจ เครื่องมือ #36",2));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(14,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(15,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(16,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(18,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(19,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(20,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(21,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(22,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"ถังน้ำมันเบนซิน (30 ลิตร)",1));
        listEQ2.add(new EquipmentForJob(1,"กระติกน้ำดื่ม+ถังน้ำ",1));

        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }


    private void generateEquipmentOtherpusealant(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครี่องดูดฝุ่น",2));
        listEQ.add(new EquipmentForJob(1,"โบลเวอร์",2));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"กระดาษกาว ",48));
        listEQ0.add(new EquipmentForJob(1,"กระป๋องใส่น้ำ",4));
        listEQ0.add(new EquipmentForJob(2,"ซันไลท์ 1 ลิตร",1));
        listEQ0.add(new EquipmentForJob(3,"ปืนยิง",4));
        listEQ0.add(new EquipmentForJob(4,"เกรียงโป๊ว 2 นิ้ว",6));
        listEQ0.add(new EquipmentForJob(5,"ผ้าไมโครไฟเบอร์",3));
        listEQ0.add(new EquipmentForJob(6,"คัตเตอร์",5));
        listEQ0.add(new EquipmentForJob(7,"รถเข็น",1));
        listEQ0.add(new EquipmentForJob(8,"แบ๊คกิ้งรอท",1));
        listEQ0.add(new EquipmentForJob(9,"พียู ซีลแลนท์",2));
        listEQ0.add(new EquipmentForJob(10,"สายไฟ 2 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(11,"ทินเนอร์",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"ถุงขยะ",5));
        listEQ2.add(new EquipmentForJob(1,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentOtherSandLevel(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่องตบดิน",1));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"สามเหลี่ยม",3));
        listEQ0.add(new EquipmentForJob(1,"พลั่ว/จอบ",4));
        listEQ0.add(new EquipmentForJob(2,"กล้องระดับ+ไม้สต๊าฟ",1));
        listEQ0.add(new EquipmentForJob(3,"บุ้งกี้",4));
        listEQ0.add(new EquipmentForJob(4,"รถเข็น",1));
        listEQ0.add(new EquipmentForJob(5,"สายเอ็น",2));
        listEQ0.add(new EquipmentForJob(6,"สายไฟ 1 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(7,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(8,"สายไฟ 3 เฟส  (50 ม.)",1));

        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentOtherSteelJob(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"ไฟเบอร์ตัด 14 นิ้ว",1));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"ถุงมือ",5));
        listEQ0.add(new EquipmentForJob(1,"กรรไกรตัดเหล็ก",3));
        listEQ0.add(new EquipmentForJob(2,"คีมมัดลวด",5));
        listEQ0.add(new EquipmentForJob(3,"ลูกหมู 4 นิ้ว(ตัดเหล็ก)",1));
        listEQ0.add(new EquipmentForJob(4,"ใบตัดเหล็ก 4 นิ้ว ",3));
        listEQ0.add(new EquipmentForJob(5,"สายไฟ 1 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(6,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(7,"สายไฟ 3 เฟส  (50 ม.)",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentOtherAsphalt(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"โบลเวอร์",1));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"ยางมะตอยก้อน",1));
        listEQ0.add(new EquipmentForJob(1,"กาต้มน้ำ(สำหรับหยอดยางมะตอย)",5));
        listEQ0.add(new EquipmentForJob(2,"เตาแก๊สปิคนิค",2));
        listEQ0.add(new EquipmentForJob(3,"ถุงมือ",5));
        listEQ0.add(new EquipmentForJob(4,"เศษผ้า",10));
        listEQ0.add(new EquipmentForJob(5,"ทินเนอร์",1));
        listEQ0.add(new EquipmentForJob(6,"สายไฟ 1 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(7,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(8,"สายไฟ 3 เฟส  (50 ม.)",1));

        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));

        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentOtherCutElectricandWind(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่องสกัดไฟฟ้า",1));
        listEQ.add(new EquipmentForJob(1,"โบลเวอร์",1));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"ลูกหมู 4 นิ้ว",1));
        listEQ0.add(new EquipmentForJob(1,"ใบตัดปูน/ใบตัดคอนกรีต 4 นิ้ว",5));
        listEQ0.add(new EquipmentForJob(2,"เต้าตีเส้น",2));
        listEQ0.add(new EquipmentForJob(3,"น้ำยาประสาน",1));
        listEQ0.add(new EquipmentForJob(4,"กระป๋องน้ำ",5));
        listEQ0.add(new EquipmentForJob(5,"สามเหลี่ยม",2));
        listEQ0.add(new EquipmentForJob(6,"เกรียง(พลาสติก)",5));
        listEQ0.add(new EquipmentForJob(7,"เกรียง(เหล็กขัดมัน)",5));
        listEQ0.add(new EquipmentForJob(8,"สายไฟ 1 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(9,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(10,"สายไฟ 3 เฟส  (50 ม.)",1));

        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));

        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentOther4010(int rowNo,String taskTypeCode,String taskType,String desc){//เข้าแบบ
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"โบลเวอร์",1));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"สายไฟ 1 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(1,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(2,"สายไฟ 3 เฟส  (50 ม.)",1));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentOtherEpoxy(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่องยิงน้ำยาแรงดันสูง",1));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"น้ำยาอีพ๊อกซี่(ซ่อมรอยราว)",1));
        listEQ0.add(new EquipmentForJob(1,"น้ำยาอีพ๊อกซี่ NS(อุดรวยร้าว)",1));
        listEQ0.add(new EquipmentForJob(2,"ลูกหมู(ปรับรอบได้)",1));
        listEQ0.add(new EquipmentForJob(3,"ใบตัดปูน/ใบตัดคอนกรีต 4 นิ้ว",3));
        listEQ0.add(new EquipmentForJob(4,"ใบขัดเรซิน",5));
        listEQ0.add(new EquipmentForJob(5,"ทินเนอร์",1));
        listEQ0.add(new EquipmentForJob(6,"เศษผ้า",10));
        listEQ0.add(new EquipmentForJob(7,"กระป๋องใส่น้ำ",5));
        listEQ0.add(new EquipmentForJob(8,"สายไฟ 1 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(9,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(10,"สายไฟ 3 เฟส  (50 ม.)",1));

        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));

        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEquipmentOtherWax(int rowNo,String taskTypeCode,String taskType,String desc){
        List<EquipmentForJob> listEQ = new ArrayList<>();
        listEQ.add(new EquipmentForJob(0,"เครื่องตัด 18 นิ้ว",2));
        listEQ.add(new EquipmentForJob(1,"โบลเวอร์",1));
        listEQ.add(new EquipmentForJob(2,"หินเจียร",2));
        EquipmentForJobGroup equipmentForJobGroup = new EquipmentForJobGroup(0,"เครื่องจักร",listEQ);

        List<EquipmentForJob> listEQ0 = new ArrayList<>();
        listEQ0.add(new EquipmentForJob(0,"ลูกหมู",1));
        listEQ0.add(new EquipmentForJob(1,"หมึกเต๊า",2));
        listEQ0.add(new EquipmentForJob(2,"สายยาง         (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(3,"กระป๋องใส่น้ำ",10));
        listEQ0.add(new EquipmentForJob(4,"ผ้าไมโครไฟเบอร์",3));
        listEQ0.add(new EquipmentForJob(5,"ใบตัด 18 นิ้ว (10 มิล)",3));
        listEQ0.add(new EquipmentForJob(6,"ใบตัดหินเจียร ",3));
        listEQ0.add(new EquipmentForJob(7,"สายไฟ 1 เฟส  (50 ม.)",4));
        listEQ0.add(new EquipmentForJob(8,"สายไฟ 3 เฟส  (100 ม.)",1));
        listEQ0.add(new EquipmentForJob(9,"สายไฟ 3 เฟส  (50 ม.)",1));
        listEQ0.add(new EquipmentForJob(10,"สามทาง 1 เฟส",1));
        listEQ0.add(new EquipmentForJob(11,"สามทาง 3 เฟส",1));
        listEQ0.add(new EquipmentForJob(12,"ไม้กวาด",2));
        listEQ0.add(new EquipmentForJob(13,"ไม้รีดน้ำ",3));
        EquipmentForJobGroup equipmentForJobGroup1 = new EquipmentForJobGroup(0,"อุปกรณ์",listEQ0);

        List<EquipmentForJob> listEQ1 = new ArrayList<>();
        listEQ1.add(new EquipmentForJob(0,"ประแจ เครื่องมือ #8",2));
        listEQ1.add(new EquipmentForJob(1,"ประแจ เครื่องมือ #10",2));
        listEQ1.add(new EquipmentForJob(2,"ประแจ เครื่องมือ #12",2));
        listEQ1.add(new EquipmentForJob(3,"ประแจ เครื่องมือ #13",2));
        listEQ1.add(new EquipmentForJob(4,"ประแจ เครื่องมือ #14",2));
        listEQ1.add(new EquipmentForJob(5,"ประแจ เครื่องมือ #16",2));
        listEQ1.add(new EquipmentForJob(6,"ประแจ เครื่องมือ #17",2));
        listEQ1.add(new EquipmentForJob(7,"ประแจ เครื่องมือ #19",2));
        listEQ1.add(new EquipmentForJob(8,"ประแจ เครื่องมือ #21",2));
        listEQ1.add(new EquipmentForJob(9,"ประแจที #8",1));
        listEQ1.add(new EquipmentForJob(10,"ประแจที #10",1));
        listEQ1.add(new EquipmentForJob(11,"ประแจที #12",1));
        listEQ1.add(new EquipmentForJob(12,"ประแจเลื่อน",1));
        listEQ1.add(new EquipmentForJob(13,"ประแจหกเหลี่ยม #1.5-10",1));
        listEQ1.add(new EquipmentForJob(14,"คีมล็อค",1));
        listEQ1.add(new EquipmentForJob(15,"บล๊อคหัวเทียน 9 HP",1));
        listEQ1.add(new EquipmentForJob(16,"ไขควง วัดไฟ ",1));
        listEQ1.add(new EquipmentForJob(17,"ไขควง แบน+แฉก",1));
        listEQ1.add(new EquipmentForJob(18,"คีมปากแหลม",1));
        listEQ1.add(new EquipmentForJob(19,"ค้อน",1));
        listEQ1.add(new EquipmentForJob(20,"ตลับเมตร",1));
        listEQ1.add(new EquipmentForJob(21,"กระเป๋าเครื่องมือ",1));
        EquipmentForJobGroup equipmentForJobGroup2 = new EquipmentForJobGroup(1,"เครื่องมือช่าง",listEQ1);

        List<EquipmentForJob> listEQ2 = new ArrayList<>();
        listEQ2.add(new EquipmentForJob(0,"กระติกน้ำดื่ม+ถังน้ำ",1));
        EquipmentForJobGroup equipmentForJobGroup3 = new EquipmentForJobGroup(2,"อื่นๆ",listEQ2);

        List<EquipmentForJobGroup> equipmentForJobGroups = new ArrayList<>();
        equipmentForJobGroups.add(equipmentForJobGroup);
        equipmentForJobGroups.add(equipmentForJobGroup1);
        equipmentForJobGroups.add(equipmentForJobGroup2);
        equipmentForJobGroups.add(equipmentForJobGroup3);
        EquipmentForJobListByTaskType equipmentForJobListByTaskType = new EquipmentForJobListByTaskType(rowNo,taskTypeCode,taskType,desc,equipmentForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_EQUIPMENT).child(taskTypeCode).setValue(equipmentForJobListByTaskType);

    }

    private void generateEnvironmentOther401(int rowNo,String taskTypeCode,String taskType){

        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();


        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,true));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,true));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,true));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,true));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,true));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);


        //environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();

        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ทำการปรับระดับดินให้เรียบร้อย",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"สิ่งกีดขวางการทำงาน",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"การกั้นพื้นที่การทำงาน",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ไลน์และระดับอ้างอิงที่ชัดเจน",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"หลังแบบได้ระดับตามจุดอ้างอิงคลาดเคลื่อนไม่เกิน +-4 mm",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"ตรวจสอบการยึดแบบที่แน่นหนา",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"แบบไม่คดงอได้เป็นแนวตรง",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEnvironmentOther402(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();


        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);

        //environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"เทลีนหรือปรับระดับดิน สูงต่ำไม่เกิน ± 1 cm.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"สิ่งกีดขวางการทำงาน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"การกั้นพื้นที่การทำงาน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ไลน์และระดับอ้างอิงที่ชัดเจน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"กรณีผูกเหล็กต้องเว้นระยะ 15-20 cm.",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"หลังแบบได้ระดับตามจุดอ้างอิงคลาดเคลื่อนไม่เกิน +-4 mm",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ตรวจสอบการยึดชิ้น Armour Joint ที่แน่นหนา",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"แบบไม่คดงอได้เป็นแนวตรง",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"ใส่พลาสติกครอบเพลสในทิศทางที่ถูกต้องและครบถ้วน",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"ตรวจสอบแฟลตบาร์ต้องแนบสนิทและเสมอกันลูบต้องไม่สดุดมือ",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"ตรวจสอบหนวดกุ้งต้องอยู่ในสภาพดีและครบถ้วนทุกตัว",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEnvironmentOther403(int rowNo,String taskTypeCode,String taskType){

        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();

        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);

        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คเครื่องม็อบพื้นว่าทำงานได้",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจสอบจุดรั่วของสายไฟ",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจสอบ ถาดขัดว่ามั่นคงไม่หลุดขณะขัด",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(1,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);
//environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ต้องเคลียร์สิ่งกีดขวางออกให้หมด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ต้องไม่มีปลั๊กไฟที่พื้น",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"พื้นที่ต้องไม่มีไฟฟ้ารั่วเพราะต้องใช้น้ำล้างพื้น",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ต้องห่อหุ้มหรือ Protection วัสดุหรือสิ่งของเพื่อไม่ให้สกปรกหรือเปียกน้ำ",true,false,false));

        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"พื้นต้องสะอาดไม่มีคราบโคลนและฝุ่น",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"แว็กซ์ต้องมีความสม่ำเสมอไม่เป็นคราบหรือรอยด่าง",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ความสะอาดโดยรอบ",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);



        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }

    private void generateEnvironmentOther404(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();
        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจสอบทดลองสตาร์ทเครื่องยนต์ว่าใช้ได้",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"ตรวจสอบน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ตรวจสอบกรองอากาศต้องไม่ตัน",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"ตรวจสอบล้อต้องอยู่ในสภาพใช้งานได้",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(4,"ตรวจสอบสายพาน ต้องไม่ชำรุดหรือรอยขาด",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(5,"ตรวจสอบการหมุนของใบตัดต้องไม่สะบัด",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(0,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);
//environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"พื้นที่ทำงานต้องไม่มีน้ำขัง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"พื้นที่ทำงานต้องไม่มีน้ำขัง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"ต้องไม่ใกล้กับวัตถุไวไฟ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ต้องไม่มีไฟรั่วไหลที่พื้น",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"ต้องมีการ Protection เรื่องฝุ่น",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"ต้องไม่ใช่ที่อับอากาศ",true,false,false));

        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"แนวการตัดต้องตรง ไม่คดเคี้ยว",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ความลึกต้องได้ตามที่กำหนด 1/4 D(หรือลูกค้ากำหนด)",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"ตขอบต้องไม่บิ่นมากเกินไป",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"ความสะอาดหลังปฏิบัติงาน",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);
    }

    private void generateEnvironmentOther405(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();


        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);

        //environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"พื้นที่ทำงานต้องไม่มีน้ำขัง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ต้องไม่มีสิ่งกีดขวาง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"ต้องกั้นพื้นที่ ห้ามคนเหยียบ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ต้องทำความสะอาดพื้นก่อนปฏิบัติงาน",true,false,false));

        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"พียูที่ยิงไปต้องเต็มร่อง",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"ต้องไม่เลอะเทอะ สกปรก",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ขอบตัดตรงทางแยกต้องสวยชัดเจน",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);
    }

    private void generateEnvironmentOther407(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();

        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);



        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"เครื่องฉีดน้ำยาต้องอยู่ในสภาพใช้งานได้",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"กระบอกใส่น้ำยาต้องไม่มีรอยรั่ว",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"สายไฟต้องไม่ชำรุด",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(3,"สายอัดน้ำยาต้องอยู่ในสภาพใช้งานได้ ไม่ตัน",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(1,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);
//environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"พื้นที่ทำงานต้องไม่มีน้ำขัง",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"รอยร้าวต้องมีความกว้าง 3 mm. ขึ้นไป",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"ต้องแน่ใจว่าน้ำยาต้องมีจุดสิ้นสุด",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"กรณีร้าวพื้นชั้นสอง ต้องอุดรอยราวด้านล่างของแผ่นพื้นก่อนด้วย NS",false,true,false));

        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"น้ำยาต้องเต็มร่อง",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"ความสะอาดหลังจบงาน",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ต้องเจียรแต่งแนวรอยราวที่อุดEpoxy NS ไว้",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);
    }

    private void generateEnvironmentOther408(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();

        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);


        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"ตรวจสอบหัวแย็คพร้องใช้งาน",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"สายลมไม่รั่ว",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"สภาพเครื่องลมไม่เสียหาย",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(1,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);
//environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"พื้นที่ทำงานไม่มีน้ำขัง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"กั้นพื้นที่ทำงานชัดเจน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"Protection ฝุ่นเศษปูนกระเด็น",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ไม่มีสิ่งกีดขวางพื้นที่ทำงาน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"ไม่ใกล้วัตถุไวไฟหรือแหล่งจ่ายน้ำมัน",true,false,false));

        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"ความลึกที่สกัดเป็นไปตามที่กำหนด",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ขอบตัดต้องเป็นแนวตรง ห้ามแตกหรือบิ่น",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ความสะอาด เก็บเศษปูน เป่าฝุ่น ดูดฝุ่น",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);
    }

    private void generateEnvironmentOther409(int rowNo,String taskTypeCode,String taskType){
        List<EnvironmentCheckListForJobGroup> environmentCheckListForJobGroups = new ArrayList<>();

        List<EnvironmentCheckListForJob> environmentCheckListForJobs0 = new ArrayList<>();
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(0,"ตรวจเช็คน้ำมันเครื่อง",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(1,"ตรวจเช็คยางลม",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(2,"ตรวจเช็คเบรค",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(3,"ตรวจเช็คระบบไฟ",true,false,false));
        environmentCheckListForJobs0.add(new EnvironmentCheckListForJob(4,"ตรวจเช็คหม้อน้ำ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup0 = new EnvironmentCheckListForJobGroup(0,"ส่วนตัวรถ",environmentCheckListForJobs0);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup0);


        List<EnvironmentCheckListForJob> environmentCheckListForJobs = new ArrayList<>();
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(0,"เครื่องสกัดสภาพพร้อมใช้งาน",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(1,"สายไฟไม่รั่ว",true,false,false));
        environmentCheckListForJobs.add(new EnvironmentCheckListForJob(2,"ดอกสกัดแหลมคม แข็งแรงไม่หักงอ",true,false,false));
        EnvironmentCheckListForJobGroup environmentCheckListForJobGroup = new EnvironmentCheckListForJobGroup(1,"เครื่องจักร",environmentCheckListForJobs);
        environmentCheckListForJobGroups.add(environmentCheckListForJobGroup);

        EnvironmentCheckListForJobListByTaskType environmentCheckListForJobListByTaskType = new EnvironmentCheckListForJobListByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                environmentCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_LIST).child(taskTypeCode).setValue(environmentCheckListForJobListByTaskType);
//environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"พื้นที่ทำงานไม่มีน้ำขัง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"กั้นพื้นที่ทำงานชัดเจน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"Protection ฝุ่นเศษปูนกระเด็น",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ไม่มีสิ่งกีดขวางพื้นที่ทำงาน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"ไม่ใกล้วัตถุไวไฟหรือแหล่งจ่ายน้ำมัน",true,false,false));

        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"ความลึกที่สกัดเป็นไปตามที่กำหนด",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ขอบตัดต้องเป็นแนวตรง ห้ามแตกหรือบิ่น",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ความสะอาด เก็บเศษปูน เป่าฝุ่น ดูดฝุ่น",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"สภาพไซค์",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_CHECK_SITE_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);
    }


    private void qc201_204_205(int rowNo,String taskType, String taskTypeCode){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ระดับหลังไกด์ลาย",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ขนาดเหล็กไกด์ลาย (เหล็กฉากขนาดไม่น้อยกว่า 1\"x1\" หนา4mm.)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"ขนาดเหล็กขาไกด์",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ระยะช่วงไกด์ (ไม่เกิน6.00m.)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"ค่ายุบตัวของคอนกรีต (Slump) เป็นไปตามมาตรฐานที่กำหนด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"Slump 13±1cm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"การป้องกันอุณหภูมิจากลม (Wind Break)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"การทำหลังคา (ทำงานในร่ม)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"การออกแบบการเทเป็นไปตามทิศทางที่ถูกต้อง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"ดีไซน์แผนการเทไปในทิศทางทีถูกต้องเพื่อป้องกันการเกิดโคจอยต์",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"ปฏิบัติงานตาม WI",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"ปฏิบัติงานด้วยความปลอดภัย",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(12,"พฤติกรรมพนักงานเป็นไปตามกฎระเบียบ",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(13,"ระดับจุดอ้างอิง",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(14,"ระดับขอบ",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(15,"ค่าระดับเมื่อปาดเสร็จ",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(16,"ความสะอาดบริเวณทำงาน",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(17,"ค่า FEFL(กรณีเป็นพื้นเรียบไม่มีความลาดชัน)",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(0,"ตรวจสอบงาน",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_QC_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }

    private void qc202(int rowNo,String taskType, String taskTypeCode){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"การป้องกันอุณหภูมิจากลม (Wind Break)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"การทำหลังคา (ทำงานในร่ม)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"การออกแบบการเทเป็นไปตามทิศทางที่ถูกต้อง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ดีไซน์แผนการเทไปในทิศทางทีถูกต้องเพื่อป้องกันการเกิดโคจอยต์",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"ปฏิบัติงานตาม WI",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"ปฏิบัติงานด้วยความปลอดภัย",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"พฤติกรรมพนักงานเป็นไปตามกฎระเบียบ",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"ระดับจุดอ้างอิง",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"ระดับขอบ",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"ค่าระดับเมื่อปาดเสร็จ",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"ความสะอาดบริเวณทำงาน",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"ค่า FEFL(กรณีเป็นพื้นเรียบไม่มีความลาดชัน)",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(0,"ตรวจสอบงาน",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_QC_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }


    private void qc203(int rowNo,String taskType, String taskTypeCode){
        //environtment
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"ขนาดเหล็ก และ ระยะ ผูกเหล็ก ต้องเป็นไปตามมาตรฐาน",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"Ø9 mm @ 150 mm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"Ø12 mm @150-250 mm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ระยะเสริมและความแข็งแรงของตีนกา ระยะความห่าง 60-80 cm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"ขนาดเหล็ก Ø≤12 mm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"ระยะความห่าง 60-80 cm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"ค่ายุบตัวของคอนกรีต (Slump) เป็นไปตามมาตรฐานที่กำหนด",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"Slump 13±1cm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"การป้องกันอุณหภูมิจากลม (Wind Break)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"การทำหลังคา (ทำงานในร่ม)",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"สิ่งกีดขวางการเดินแสงเลเซอร์",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"ระยะ Concrete covering เป็นไปตามที่กำหนด 2.5 - 3.0 cm",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(12,"การออกแบบการเทเป็นไปตามทิศทางที่ถูกต้อง",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(13,"บริเวณด้านพิ้นที่เทคอนกรีตไม่มีการทำงานให้สั่นสะเทือนหรือทำลายสัญญาณเลเซอร์",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(14,"ดีไซน์แผนการเทไปในทิศทางทีถูกต้องเพื่อป้องกันการเกิดโคจอยต์",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(15,"ปฏิบัติงานตาม WI",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(16,"ปฏิบัติงานด้วยความปลอดภัย",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(17,"พฤติกรรมพนักงานเป็นไปตามกฎระเบียบ",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(18,"ระดับจุดอ้างอิง",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(19,"ระดับขอบ",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(20,"ค่าระดับเมื่อปาดเสร็จ",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(21,"ความสะอาดบริเวณทำงาน",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(22,"ค่า FEFL(กรณีเป็นพื้นเรียบไม่มีความลาดชัน)",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup0 = new WorkingCheckListForJobGroup(0,"ตรวจสอบงาน",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup0);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_QC_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }

    private void qc301_302(int rowNo,String taskType, String taskTypeCode){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"คอนกรีตที่ใช้มีกำลังอัดตามข้อกำหนดสำหรับงานผิวคอนกรีตต่างๆที่ต้องการ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ค่ายุบตัวของคอนกรีต (Slump) เป็นไปตามาตรฐานที่กำหนด",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"Slump 13±1cm",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ปริมาณและอัตราการเทในแต่ละครั้งไม่เกิน 1200 m²",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"การทำหลังคา ทำงานในร่ม",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"การเตรียมแสงสว่างเพียงพอต่อการทำงาน",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"การเตรียมแผนรับมือเมื่อเกิดฝนตก กรณีงานกลางแจ้ง",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"จุดใช้น้ำอยู่ใกล้พื้นที่ทำงาน ไม่เกิน20เมตร",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"จดีไซน์แผนการเทไปในทิศทางทีถูกต้องเพื่อป้องกันการเกิดโคจอยต์",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"ปฎิบัติตามขั้นตอน WI",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"ระดับขอบ ",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"โคจอยต์",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(12,"คอนกรีตที่ใช้มีกำลังอัดตามข้อกำหนดสำหรับงานผิวคอนกรีตต่างๆที่ต้องการ",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(13,"ระดับFinishing,ขอบหลังขัดเสร็จ (ใช้ตำแหน่งเดียวกันกับงานปาด)",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(14,"ความเรียบขอบ(ประจักษด้วยสาย)",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(15,"ความสะอาดบริเวณทำงาน",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(0,"ตรวจสอบงาน",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_QC_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }
    private void qc303_304_305(int rowNo,String taskType, String taskTypeCode){
        List<WorkingCheckListForJobGroup> workingCheckListForJobGroups = new ArrayList<>();
        List<WorkingCheckListForJob> workingCheckListForJobs0 = new ArrayList<>();
        workingCheckListForJobs0.add(new WorkingCheckListForJob(0,"คอนกรีตที่ใช้มีกำลังอัดตามข้อกำหนดสำหรับงานผิวคอนกรีตต่างๆที่ต้องการ",true,false,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(1,"ค่ายุบตัวของคอนกรีต (Slump) เป็นไปตามาตรฐานที่กำหนด",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(2,"Slump 13±1cm",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(3,"ปริมาณและอัตราการเทในแต่ละครั้งไม่เกิน 1200 m²",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(4,"การทำหลังคา ทำงานในร่ม",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(5,"การเตรียมแสงสว่างเพียงพอต่อการทำงาน",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(6,"การเตรียมแผนรับมือเมื่อเกิดฝนตก กรณีงานกลางแจ้ง",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(7,"จุดใช้น้ำอยู่ใกล้พื้นที่ทำงาน ไม่เกิน20เมตร",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(8,"จดีไซน์แผนการเทไปในทิศทางทีถูกต้องเพื่อป้องกันการเกิดโคจอยต์",true,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(9,"ปฎิบัติตามขั้นตอน WI",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(10,"ระดับขอบ ",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(11,"โคจอยต์",false,true,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(12,"คอนกรีตที่ใช้มีกำลังอัดตามข้อกำหนดสำหรับงานผิวคอนกรีตต่างๆที่ต้องการ",false,true,false));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(13,"ระดับFinishing,ขอบหลังขัดเสร็จ (ใช้ตำแหน่งเดียวกันกับงานปาด)",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(14,"ความเงาโดยรวม(ประจักษด้วยสาย)",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(15,"ความเงาขอบ(ประจักษด้วยสาย)",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(16,"ความเรียบขอบ(ประจักษด้วยสาย)",false,false,true));
        workingCheckListForJobs0.add(new WorkingCheckListForJob(17,"ความสะอาดบริเวณทำงาน",false,false,true));
        WorkingCheckListForJobGroup workingCheckListForJobGroup = new WorkingCheckListForJobGroup(0,"ตรวจสอบงาน",workingCheckListForJobs0);
        workingCheckListForJobGroups.add(workingCheckListForJobGroup);

        WorkingCheckListForJoblistByTaskType workingCheckListForJoblistByTaskType = new WorkingCheckListForJoblistByTaskType(rowNo,
                taskTypeCode,
                taskType,
                "",
                workingCheckListForJobGroups);
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_MASTER_TEMPLATE).child(AppConstant.DB_TEMPLATE_QC_LIST).child(taskTypeCode).setValue(workingCheckListForJoblistByTaskType);

    }
    public void createControlDate(){
        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataRow: dataSnapshot.getChildren()){
                    TaskDataEntry taskDataEntry = dataRow.getValue(TaskDataEntry.class);
                    taskDataEntry.setCompareTaskDate(taskDataEntry.getTaskDate().substring(6)+taskDataEntry.getTaskDate().substring(3,5)+taskDataEntry.getTaskDate().substring(0,2));
                    databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).child(taskDataEntry.getTaskID()).setValue(taskDataEntry);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clearAllTask(){
//        databaseReference.child(AppConstant.DB_ROOT).child(AppConstant.DB_TASK_LIST).setValue("");
    }
}
