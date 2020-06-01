/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.publicis.sapient.product.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.publicis.sapient.product.model.Product;
import com.publicis.sapient.product.model.ProductCategory;

/**
 * @author sursuhas
 * @author prejayak
 * @author sidshriv3
 * @since March 2020
 * @see Repository for Product Collection
 */

public interface ProductRepository extends MongoRepository<Product, String> {

	@Query(value = "{'productName':?0}")
	public Product getProductByName(String productName);

	@Query(value = "{'productCategory':?0}")
	public List<Product> getProductsByCategory(ProductCategory productCategory);

	@Query(value = "{$and:[{'assignProduct.promotions.startDate':{$gte:?0}},{'assignProduct.promotions.endDate':{$lte:?1}}]}")
	public List<Product> findAllProductsIntheDateRangeZone(Date startDate, Date endDate);

	@Query(value = "{$and:[{'assignProduct.cluster.promotions.startDate':{$gte:?0}},{'assignProduct.cluster.promotions.endDate':{$lte:?1}}]}")
	public List<Product> findAllProductsIntheDateRangeCluster(Date startDate, Date endDate);

	@Query(value = "{$and:[{'assignProduct.promotions.startDate':{$gt:?2},'assignProduct.promotions.endDate':{$gt:?2},$or:[{'assignProduct.promotions.startDate':{$gt:?0}},{'assignProduct.promotions.startDate':?0}],$or:[{'assignProduct.promotions.endDate':{$lt:?1}},{'assignProduct.promotions.endDate':?1}]}]}")
	public List<Product> findAllProductsIntheDateRangeFutureZone(Date startDate, Date endDate, Date currentDate);

	@Query(value = "{$and:[{'assignProduct.cluster.promotions.startDate':{$gt:?2},'assignProduct.cluster.promotions.endDate':{$gt:?2},$or:[{'assignProduct.cluster.promotions.startDate':{$gt:?0}},{'assignProduct.cluster.promotions.startDate':?0}],$or:[{'assignProduct.cluster.promotions.endDate':{$lt:?1}},{'assignProduct.cluster.promotions.endDate':?1}]}]}")
	public List<Product> findAllProductsIntheDateRangeFutureCluster(Date startDate, Date endDate, Date currentDate);

	@Query(value = "{'productCategory':{$ne:?0}}")
	public List<Product> getNonAlcoholProductByName(ProductCategory category);

	@Query(value = "{'companyName':?0}")
	public List<Product> getProductsByVendorName(final String vendorName);
}
