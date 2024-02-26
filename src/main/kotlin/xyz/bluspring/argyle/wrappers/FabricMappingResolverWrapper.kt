package xyz.bluspring.argyle.wrappers

import net.fabricmc.loader.api.MappingResolver

class FabricMappingResolverWrapper(val wrapped: MappingResolver) : org.quiltmc.loader.api.MappingResolver {
    override fun getNamespaces(): MutableCollection<String> {
        return wrapped.namespaces
    }

    override fun getCurrentRuntimeNamespace(): String {
        return wrapped.currentRuntimeNamespace
    }

    override fun mapClassName(namespace: String?, className: String?): String {
        return wrapped.mapClassName(namespace, className)
    }

    override fun unmapClassName(targetNamespace: String?, className: String?): String {
        return wrapped.unmapClassName(targetNamespace, className)
    }

    override fun mapFieldName(namespace: String?, owner: String?, name: String?, descriptor: String?): String {
        return wrapped.mapFieldName(namespace, owner, name, descriptor)
    }

    override fun mapMethodName(namespace: String?, owner: String?, name: String?, descriptor: String?): String {
        return wrapped.mapMethodName(namespace, owner, name, descriptor)
    }
}