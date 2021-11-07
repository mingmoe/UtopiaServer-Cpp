//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The BlockFloorImpl.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.map;

import moe.kawayi.org.utopia.core.map.FlatPosition;
import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * 一个地图层
 * <p>
 * 线程安全
 */
public final class BlockFloorImpl implements BlockFloor {

    /**
     * 区块
     */
    @NotNull
    private final BlockImpl[][] floor;

    /**
     * 构造一个地图层
     */
    public BlockFloorImpl() {
        // 初始化区块
        floor = new BlockImpl[WorldInfo.BLOCK_FLOOR_X_SIZE][];

        for (int x = 0; x != WorldInfo.BLOCK_FLOOR_X_SIZE; x++) {
            floor[x] = new BlockImpl[WorldInfo.BLOCK_FLOOR_Y_SIZE];

            for (int y = 0; y != WorldInfo.BLOCK_FLOOR_Y_SIZE; y++)
                floor[x][y] = new BlockImpl();
        }
    }


    /**
     * 获取方块
     *
     * @param position 位置
     *                 <p>
     *                 位置范围: from 0 to ([X|Y]_SIZE-1)
     * @return 如果在范围内，返回获取到的方块，否则返回Empty
     */
    @Override
    @NotNull
    public Optional<Block> getBlock(@NotNull FlatPosition position) {
        // null check
        Objects.requireNonNull(position, "position must not be null");

        if (position.x < 0 || position.x >= WorldInfo.BLOCK_FLOOR_X_SIZE)
            return Optional.empty();

        if (position.y < 0 || position.y >= WorldInfo.BLOCK_FLOOR_Y_SIZE)
            return Optional.empty();

        return Optional.of(floor[position.x][position.y]);
    }


}
