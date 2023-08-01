package io.rapidw.iotcore.connector.mqtt.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.rapidw.iotcore.common.entity.struct.*;
import io.rapidw.iotcore.connector.mqtt.mapper.struct.*;
import io.rapidw.iotcore.connector.mqtt.mapstruct.DtoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EntryService {
    private final EntryMapper entryMapper;
    private final EntryInt32Mapper entryInt32Mapper;
    private final EntryInt64Mapper entryInt64Mapper;
    private final EntryFloatMapper entryFloatMapper;
    private final EntryDoubleMapper entryDoubleMapper;
    private final EntryStringMapper entryStringMapper;

    public EntryService(EntryMapper entryMapper, EntryInt32Mapper entryInt32Mapper, EntryInt64Mapper entryInt64Mapper, EntryFloatMapper entryFloatMapper, EntryDoubleMapper entryDoubleMapper, EntryStringMapper entryStringMapper) {
        this.entryMapper = entryMapper;
        this.entryInt32Mapper = entryInt32Mapper;
        this.entryInt64Mapper = entryInt64Mapper;
        this.entryFloatMapper = entryFloatMapper;
        this.entryDoubleMapper = entryDoubleMapper;
        this.entryStringMapper = entryStringMapper;
    }

    public Map<String, FullEntry> getAllStructEntry(Integer structId) {
        val entries = entryMapper.selectList(Wrappers.lambdaQuery(Entry.class)
                .eq(Entry::getStructId, structId));

        val map = new HashMap<String, FullEntry>();
        entries.forEach(v -> {
            val fullEntry = DtoMappers.INSTANCE.entryToFullEntry(v);
            switch (fullEntry.getType()) {
                case INT32:
                    fullEntry.setEntryInt32(entryInt32Mapper.selectOne(Wrappers.lambdaQuery(EntryInt32.class)
                            .eq(EntryInt32::getEntryId, fullEntry.getId())));
                case INT64:
                    fullEntry.setEntryInt64(entryInt64Mapper.selectOne(Wrappers.lambdaQuery(EntryInt64.class)
                            .eq(EntryInt64::getEntryId, fullEntry.getId())));
                case FLOAT:
                    fullEntry.setEntryFloat(entryFloatMapper.selectOne(Wrappers.lambdaQuery(EntryFloat.class)
                            .eq(EntryFloat::getEntryId, fullEntry.getId())));
                case DOUBLE:
                    fullEntry.setEntryDouble(entryDoubleMapper.selectOne(Wrappers.lambdaQuery(EntryDouble.class)
                            .eq(EntryDouble::getEntryId, fullEntry.getId())));
                case STRING:
                    fullEntry.setEntryString(entryStringMapper.selectOne(Wrappers.lambdaQuery(EntryString.class)
                            .eq(EntryString::getEntryId, fullEntry.getId())));
            }
            map.put(fullEntry.getIdentifier(), fullEntry);
        });
        return map;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class FullEntry extends Entry {
        private EntryInt32 entryInt32;
        private EntryInt64 entryInt64;
        private EntryFloat entryFloat;
        private EntryDouble entryDouble;
        private EntryString entryString;
    }
}
