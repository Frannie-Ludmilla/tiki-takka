/*
 * Copyright (C) 2017 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.tikitakka.common.util

import java.net.HttpCookie

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpMethods
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.testkit.TestKit
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ShouldMatchers, WordSpec, WordSpecLike}

@RunWith(classOf[JUnitRunner])
class HttpRequestUtilsTest extends TestKit(ActorSystem("HttpRequestUtils"))
  with ShouldMatchers with WordSpecLike with HttpRequestUtils {

  implicit val testSystem = ActorSystem("test")
  implicit val actorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))

  "HttpRequestUtils.createRequest" when {
    "passed a resource with a query on it" should {
      "create a correct url" in {
        val baseUrl = "https://megadev.labs.stratio.com/service/marathon"
        val resource = "v2/apps?id=anyApp/instanceName/WithWorkflows"
        val actualReq = createRequest(baseUrl, resource, HttpMethods.GET, None, Seq.empty[HttpCookie])
        actualReq._2.toString() should equal (baseUrl.concat("/"+resource))
      }
    }
    "passed a resource without query" should{
      "create a correct url" in {
        val baseUrl = "https://megadev.labs.stratio.com/service/marathon"
        val resource = "v2/groups/anyApp/instanceName/WithWorkflows"
        val actualReq = createRequest(baseUrl, resource, HttpMethods.GET, None, Seq.empty[HttpCookie])
        actualReq._2.toString() should equal (baseUrl.concat("/"+resource))
      }
    }
  }
}
