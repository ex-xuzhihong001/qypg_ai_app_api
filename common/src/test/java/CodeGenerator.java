import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String projectPath = System.getProperty("user.dir") + "/common/src/main/java";

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath);
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setSwagger2(true); // 是否开启swagger
        gc.setDateType(DateType.ONLY_DATE);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/rxy_data?characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&rewriteBatchedStatements=true&allowMultiQueries=true&useSSL=false");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("");

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setParent("com.rxy.qypg.common.dao");
        pc.setEntity("entity");
        pc.setMapper("repository");
        pc.setService("service");
        pc.setServiceImpl("service.impl");



        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        /*strategy.setInclude("wallet_symbol_config","wallet_server","symbol_config","wallet_user","wallet_address",
                "wallet_block","wallet_deposit","wallet_withdraw","wallet_energy","wallet_agg","wallet_center","wallet_provide","wallet_transfer");*/
        //strategy.setInclude("user","config_question");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setPackageInfo(pc);
        mpg.setStrategy(strategy);
        TemplateConfig templateConfig = new TemplateConfig();
        mpg.setTemplate(templateConfig);
        mpg.execute();
    }

}