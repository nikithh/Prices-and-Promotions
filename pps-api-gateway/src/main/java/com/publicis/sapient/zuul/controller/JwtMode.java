package com.publicis.sapient.zuul.controller;
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

/**
 * @author godkarte
 *
 */
public class JwtMode {
	public static class JwtModeStat{
		private static int status=0;

		public static int getStatus() {
			return status;
		}

		public static void setStatus(int status) {
			JwtModeStat.status = status;
		}
		
	}
}
