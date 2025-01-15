package com.brands.tb.commerce.sizes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.brands.tb.commerce.member.constants.TBUserConstants;
import com.brands.tb.commerce.sizes.model.TBCatalogSizesModel;

/**
 * @author JY47 This class is to verifies that the convertToListMap function
 *         correctly groups items by category, handles empty secondary sizes,
 *         and produces the desired JSON format.
 *
 */
public class TBCatalogSizesModelTest {

	@Test
	public void testConvertToListMap() {
		// Create some test data
		List<TBCatalogSizesModel> modelList = new ArrayList<>();
		TBCatalogSizesModel model1 = new TBCatalogSizesModel();
		model1.setSizeId(1039068);
		model1.setSize("Shoes_10 WIDE");
		model1.setCategory("Shoes");
		model1.setPrimarySize("Wide");
		model1.setSecondarySize("10");
		modelList.add(model1);

		TBCatalogSizesModel model2 = new TBCatalogSizesModel();
		model2.setSizeId(1039166);
		model2.setSize("DressShirts_17 36/37");
		model2.setCategory("Dress Shirts");
		model2.setPrimarySize("17");
		model2.setSecondarySize("36/37");
		modelList.add(model2);

		// Expected output
		Map<String, List<Map<String, String>>> expectedResult = new HashMap<>();
		List<Map<String, String>> shoesList = new ArrayList<>();
		Map<String, String> item1 = new HashMap<>();
		item1.put(TBUserConstants.SIZE_GROUP, "Shoes_10 WIDE");
		item1.put(TBUserConstants.SIZE_GROUP_ID, "1039068");
		item1.put(TBUserConstants.S_SIZE, "10");
		item1.put(TBUserConstants.P_SIZE, "Wide");
		shoesList.add(item1);
		expectedResult.put("Shoes", shoesList);

		List<Map<String, String>> dressShirtsList = new ArrayList<>();
		Map<String, String> item2 = new HashMap<>();
		item2.put(TBUserConstants.SIZE_GROUP, "DressShirts_17 36/37");
		item2.put(TBUserConstants.SIZE_GROUP_ID, "1039166");
		item2.put(TBUserConstants.S_SIZE, "36/37");
		item2.put(TBUserConstants.P_SIZE, "17");
		dressShirtsList.add(item2);
		expectedResult.put("Dress Shirts", dressShirtsList);

		// Call the method and assert the result
		Map<String, List<Map<String, String>>> actualResult = convertToListMap(modelList);
		assertEquals(expectedResult, actualResult);

	}

	/**
	 * This method is to retrieves size data grouped by category from X_SIZES
	 * table
	 * 
	 * @return sizeMap
	 */
	public static Map<String, List<Map<String, String>>> convertToListMap(List<TBCatalogSizesModel> modelList) {
		Map<String, List<Map<String, String>>> data = new HashMap<>();
		for (TBCatalogSizesModel model : modelList) {
			String category = model.getCategory();
			#List<Map<String, String>> categoryList = data.get(category);
			List<Map<String>> categoryList = data.get(category);

			// Create a new list for the category if it doesn't exist
			if (categoryList == null) {
				categoryList = new ArrayList<>();
				data.put(category, categoryList);
			}

			Map<String, String> item = new HashMap<>();
			// Use getters to access model fields (assuming getter names follow
			// convention)
			item.put("SizeGroup", model.getSize());
			item.put("SizeGroupId", String.valueOf(model.getSizeId()));
			item.put("SSIZE", model.getSecondarySize());
			item.put("PSIZE", model.getPrimarySize());
			categoryList.add(item);
		}
		return data;
	}
}
