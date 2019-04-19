/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2011-2018 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

/** This code is generated, do not edit by hand. **/

#include "de.appwerft.watchdog.WatchdogModule.h"

#include "AndroidUtil.h"
#include "JNIUtil.h"
#include "JSException.h"
#include "TypeConverter.h"
#include "V8Util.h"




#include "org.appcelerator.kroll.KrollModule.h"

#define TAG "WatchdogModule"

using namespace v8;

namespace de {
namespace appwerft {
namespace watchdog {


Persistent<FunctionTemplate> WatchdogModule::proxyTemplate;
jclass WatchdogModule::javaClass = NULL;

WatchdogModule::WatchdogModule() : titanium::Proxy()
{
}

void WatchdogModule::bindProxy(Local<Object> exports, Local<Context> context)
{
	Isolate* isolate = context->GetIsolate();

	Local<FunctionTemplate> pt = getProxyTemplate(isolate);

	v8::TryCatch tryCatch(isolate);
	Local<Function> constructor;
	MaybeLocal<Function> maybeConstructor = pt->GetFunction(context);
	if (!maybeConstructor.ToLocal(&constructor)) {
		titanium::V8Util::fatalException(isolate, tryCatch);
		return;
	}

	Local<String> nameSymbol = NEW_SYMBOL(isolate, "Watchdogholder"); // use symbol over string for efficiency
	MaybeLocal<Object> maybeInstance = constructor->NewInstance(context);
	Local<Object> moduleInstance;
	if (!maybeInstance.ToLocal(&moduleInstance)) {
		titanium::V8Util::fatalException(isolate, tryCatch);
		return;
	}
	exports->Set(context, nameSymbol, moduleInstance);
}

void WatchdogModule::dispose(Isolate* isolate)
{
	LOGD(TAG, "dispose()");
	if (!proxyTemplate.IsEmpty()) {
		proxyTemplate.Reset();
	}

	titanium::KrollModule::dispose(isolate);
}

Local<FunctionTemplate> WatchdogModule::getProxyTemplate(v8::Isolate* isolate)
{
	Local<Context> context = isolate->GetCurrentContext();
	if (!proxyTemplate.IsEmpty()) {
		return proxyTemplate.Get(isolate);
	}

	LOGD(TAG, "WatchdogModule::getProxyTemplate()");

	javaClass = titanium::JNIUtil::findClass("de/appwerft/watchdog/WatchdogModule");
	EscapableHandleScope scope(isolate);

	// use symbol over string for efficiency
	Local<String> nameSymbol = NEW_SYMBOL(isolate, "Watchdogholder");

	Local<FunctionTemplate> t = titanium::Proxy::inheritProxyTemplate(
		isolate,
		titanium::KrollModule::getProxyTemplate(isolate),
		javaClass,
		nameSymbol);

	proxyTemplate.Reset(isolate, t);
	t->Set(titanium::Proxy::inheritSymbol.Get(isolate), FunctionTemplate::New(isolate, titanium::Proxy::inherit<WatchdogModule>));

	// Method bindings --------------------------------------------------------

	Local<ObjectTemplate> prototypeTemplate = t->PrototypeTemplate();
	Local<ObjectTemplate> instanceTemplate = t->InstanceTemplate();

	// Delegate indexed property get and set to the Java proxy.
	instanceTemplate->SetIndexedPropertyHandler(titanium::Proxy::getIndexedProperty,
		titanium::Proxy::setIndexedProperty);

	// Constants --------------------------------------------------------------

	// Dynamic properties -----------------------------------------------------

	// Accessors --------------------------------------------------------------

	return scope.Escape(t);
}

Local<FunctionTemplate> WatchdogModule::getProxyTemplate(v8::Local<v8::Context> context)
{
	return getProxyTemplate(context->GetIsolate());
}

// Methods --------------------------------------------------------------------

// Dynamic property accessors -------------------------------------------------


} // watchdog
} // appwerft
} // de
