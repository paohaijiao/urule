package com.urule;

import com.bstek.urule.Utils;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.response.RuleExecutionResponse;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.urule.bean.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =Application.class)
public class DemoApplicationTests {

	@Test
	public void contextLoads()throws Exception {
		//从Spring中获取KnowledgeService接口实例
		KnowledgeService service=(KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
		//通过KnowledgeService接口获取指定的资源包"test123"
		KnowledgePackage knowledgePackage=service.getKnowledge("myproject/测试决策表");
		//通过取到的KnowledgePackage对象创建KnowledgeSession对象
		KnowledgeSession session= KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
		Customer cu=new Customer();
		cu.setAge(18);
		cu.setGender(true);
		//将业务数据对象Employee插入到KnowledgeSession中
		session.insert(cu);

		Map<String,Object> parameter=new HashMap<String,Object>();
		parameter.put("count", 10);
		parameter.put("result", true);
		//触发规则时并设置参数
		RuleExecutionResponse response=session.fireRules(parameter);

		//获取计算后的result值，要通过KnowledgeSession,而不能通过原来的parameter对象
		boolean result=(Boolean)session.getParameter("result");
		System.out.println(result);
		System.out.println(cu.getLevel());
	}

}
