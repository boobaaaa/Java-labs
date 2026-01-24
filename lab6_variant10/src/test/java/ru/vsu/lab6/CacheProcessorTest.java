package ru.vsu.lab6;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.vsu.lab6.annotations.Cache;
import ru.vsu.lab6.processors.CacheProcessor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CacheProcessorTest {

    interface CacheBackend {
        void put(String region, Object value);
    }

    @Cache({"users", "orders"})
    static class MultiRegionService {
        void warmUp(CacheBackend backend) {
            backend.put("users", "u1");
            backend.put("orders", "o1");
        }
    }

    @Cache({})
    static class EmptyRegionService {
        void warmUp(CacheBackend backend) {
        }
    }

    @Test
    void readRegions_shouldReturnMultipleRegions() {
        List<String> regions = CacheProcessor.readRegions(MultiRegionService.class);
        assertEquals(List.of("users", "orders"), regions);
    }

    @Test
    void warmUp_withMock_shouldPutDataForEachRegion() {
        CacheBackend backend = Mockito.mock(CacheBackend.class);
        new MultiRegionService().warmUp(backend);

        verify(backend, times(1)).put("users", "u1");
        verify(backend, times(1)).put("orders", "o1");
        verifyNoMoreInteractions(backend);
    }

    @Test
    void emptyRegions_shouldNotCacheAnything() {
        List<String> regions = CacheProcessor.readRegions(EmptyRegionService.class);
        assertTrue(regions.isEmpty());

        CacheBackend backend = Mockito.mock(CacheBackend.class);
        new EmptyRegionService().warmUp(backend);

        verifyNoInteractions(backend);
    }
}
