package com.life.kit.system.json

import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.usertype.UserType
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.io.StringWriter
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types
import kotlin.jvm.Throws

class ExtraJsonType : UserType {

    private val mapper = ObjectMapper()

    override fun sqlTypes(): IntArray {
        return intArrayOf(Types.JAVA_OBJECT)
    }

    override fun returnedClass(): Class<ExtraJson> {
        return ExtraJson::class.java
    }

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeGet(resultSet: ResultSet,
                             strings: Array<String>,
                             sharedSessionContractImplementor: SharedSessionContractImplementor,
                             o: Any): Any? {
        val cellContent = resultSet.getString(strings[0]) ?: return null
        return try {
            mapper.readValue(cellContent.toByteArray(charset("UTF-8")), returnedClass())
        } catch (ex: Exception) {
            throw RuntimeException("Failed to convert String to Json: " + ex.message, ex)
        }
    }

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeSet(preparedStatement: PreparedStatement, o: Any?, i: Int,
                             sharedSessionContractImplementor: SharedSessionContractImplementor) {
        if (o == null) {
            preparedStatement.setNull(i, Types.OTHER)
            return
        }
        try {
            val stringWriter = StringWriter()
            mapper.writeValue(stringWriter, o)
            stringWriter.flush()
            preparedStatement.setObject(i, stringWriter.toString(), Types.OTHER)
        } catch (ex: Exception) {
            throw RuntimeException("Failed to convert Json to String: " + ex.message, ex)
        }
    }

    @Throws(HibernateException::class)
    override fun deepCopy(value: Any): Any {
        return try {
            // use serialization to create a deep copy
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(value)
            objectOutputStream.flush()
            objectOutputStream.close()
            byteArrayOutputStream.close()
            val byteArrayInputStream = ByteArrayInputStream(byteArrayOutputStream.toByteArray())
            ObjectInputStream(byteArrayInputStream).readObject()
        } catch (ex: ClassNotFoundException) {
            throw HibernateException(ex)
        } catch (ex: IOException) {
            throw HibernateException(ex)
        }
    }

    override fun isMutable(): Boolean {
        return true
    }

    @Throws(HibernateException::class)
    override fun disassemble(value: Any): Serializable {
        return deepCopy(value) as Serializable
    }

    @Throws(HibernateException::class)
    override fun assemble(cached: Serializable, owner: Any): Any {
        return deepCopy(cached)
    }

    @Throws(HibernateException::class)
    override fun replace(original: Any, target: Any, owner: Any): Any {
        return deepCopy(original)
    }

    @Throws(HibernateException::class)
    override fun equals(obj1: Any?, obj2: Any?): Boolean {
        return if (obj1 == null) {
            obj2 == null
        } else obj1 == obj2
    }

    @Throws(HibernateException::class)
    override fun hashCode(obj: Any): Int {
        return obj.hashCode()
    }
}