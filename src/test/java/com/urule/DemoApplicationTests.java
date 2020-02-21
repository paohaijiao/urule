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
		KnowledgeService service=(KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
		KnowledgePackage knowledgePackage=service.getKnowledge("myproject/测试决策表");
		KnowledgeSession session= KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
		Customer cu=new Customer();
		cu.setAge(18);
		cu.setGender(true);
		session.insert(cu);

		Map<String,Object> parameter=new HashMap<String,Object>();
		parameter.put("count", 10);
		parameter.put("result", true);
		RuleExecutionResponse response=session.fireRules(parameter);
		boolean result=(Boolean)session.getParameter("result");
		System.out.println(result);
		System.out.println(cu.getLevel());
	}

}
