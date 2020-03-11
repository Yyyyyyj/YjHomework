package com.taotao.classtest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class PokerGame {
	
	@Test
	public void diffRank() {
		assertTrue(texasHoldemPoker("2H 4S 4C 2D 4H", "2S 8S AS QS 3S") == "Black wins");
	}
	
	@Test
	public void sameRank1DiffStart() {
		assertTrue(texasHoldemPoker("2H 3D 5S 9C KD", "2C 3H 4S 8C AH") == "White wins");
	}
	
	@Test
	public void sameRank4DiffStart() {
		assertTrue(texasHoldemPoker("6H 6D 6S QH 3D", "6H 6D 6S JD 8A") == "Black wins");
	}
	
	@Test
	public void sameRank1SameStart() {
		assertTrue(texasHoldemPoker("2H 3D 5S 9C KD", "2C 3H 4S 8C KH") == "Black wins");
	}
	
	@Test
	public void sameRankSameValues() {
		assertTrue(texasHoldemPoker("2H 3D 5S 9C KD", "2D 3H 5C 9S KH") == "Tie");
	}

	public String texasHoldemPoker(String black, String white) {
		
		//记录有多少种牌点
		int whiteValueType = 0;
		int blackValueType = 0;
		//记录有多少种花色
		int whiteSuitType = 0;
		int blackSuitType = 0;
		//用list下标i代表重复次数，用HashSet有序记录重复了i+1次的数字
		List<HashSet<Integer>> whitevalues = new ArrayList<>();
		List<HashSet<Integer>> blackvalues = new ArrayList<>();
		
		//处理输入数据,返回统计好的花色种类和牌点种类
		List<Integer> whiteCount = dataProcess(white,whitevalues);
		whiteSuitType = whiteCount.get(0);
		whiteValueType = whiteCount.get(1);
		List<Integer> blackCount = dataProcess(black, blackvalues);
		blackSuitType = blackCount.get(0);
		blackValueType = blackCount.get(1);
		
		//假设牌面总共有九个等级,取等级数
		//同花顺(9),铁支(8),葫芦(7),同花(6),顺子(5),三条(4),两对(3),对子(2),散牌(1)
		int whiteRank = getRank(whiteValueType,whiteSuitType,whitevalues);
		int blackRank = getRank( blackValueType, blackSuitType, blackvalues);
		
		if(blackRank > whiteRank) {
			return "Black wins";
		}
		else if(blackRank < whiteRank) {
			return "White wins";
		}else {
			for(int i=whitevalues.size()-1; i>=0; i--) {
				if(whitevalues.get(i).size()>0) {
					List<Integer> whiteList = new ArrayList<Integer>(whitevalues.get(i));
					List<Integer> blackList = new ArrayList<Integer>(blackvalues.get(i));
					for(int j=whiteList.size()-1; j>=0; j--) {
						if(blackList.get(j) > whiteList.get(j)) {
							return "Black wins";
						}
						if(blackList.get(j) < whiteList.get(j)) {
							return "White wins";
						}
					}
				}
			}
		}
		
		return "Tie";
	}
	
	private List<Integer> dataProcess(String s, List<HashSet<Integer>> values) {
		
		List<Integer> res = new ArrayList<Integer>();
		
		HashMap<Character, String> map = new HashMap<Character, String>();
		map.put('A',"14");
		map.put('K',"13");
		map.put('Q',"12");
		map.put('J',"11");
		map.put('T',"10");
		
		HashSet<Character> suitSet = new HashSet<>();
		HashMap<Integer,Integer> valueMap = new HashMap<>();
		for(int i=0;i<14;i=i+3) {
			
			//将牌点转化为整形后统计每种牌点的出现次数
			int value = 0;
			if(map.containsKey(s.charAt(i))) {
				value = Integer.parseInt(map.get(s.charAt(i)));
			}else {
				value = s.charAt(i) - '0';
			}
			valueMap.put(value, valueMap.getOrDefault(value, 0) + 1);
			
			//统计花色种类
			suitSet.add(s.charAt(i+1));
		}
		
		int suitType = suitSet.size();
		int valueType = valueMap.size();
		res.add(suitType);
		res.add(valueType);
		
		//准备能有序存放牌点的set
		for(int i=0; i<(5-valueType+1); i++) {
			values.add(new HashSet<Integer>());
		}
		
		//记录出现0次、1次.。。。的牌点
		for(int value: valueMap.keySet()) {
			int count = valueMap.get(value);
			HashSet<Integer> set = values.get(count-1);
			set.add(value);
		}
		
		return res;
	}
	
	private int getRank( int valueType, int suitType, List<HashSet<Integer>> values) {
		int rank = 1;
		
		if(valueType == 5) {
			List<Integer> list = new ArrayList<Integer>(values.get(0));
			//顺子
			if(list.get(0)==list.get(4)-4) {
				//同花顺
				if(suitType == 1) {
					return 9; 
				}
				return 5;
			}
		}
		
		//对子
		if(valueType == 4) {
			return 2;
		}
		
		if(valueType == 3) {
			//查牌点中重复数字的最大出现次数
			int times = values.size()-1;
			while(values.get(times).size()==0) {
				times--;
			}
			//下标为i表示重复了i+1次
			times = times + 1;
			//两对
			if(times == 2) {
				return 3;
			}
			//三条
			if(times == 3) {
				return 4;
			}
		}
		
		if(valueType == 2) {
			int times = values.size()-1;
			while(values.get(times).size()==0) {
				times--;
			}
			times = times + 1;
			//铁支
			if(times == 4) {
				return 8;
			}
			//葫芦
			if(times == 3) {
				return 7;
			}
		}
		
		//同花
		if(suitType == 1) {
			return 6;
		}
		return rank;
	}

}
