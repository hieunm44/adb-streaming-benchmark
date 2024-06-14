/**************************************************************************************
 *  Copyright (c) 2019- Gabriele Mencagli
 *  
 *  This file is part of StreamBenchmarks.
 *  
 *  StreamBenchmarks is free software dual licensed under the GNU LGPL or MIT License.
 *  You can redistribute it and/or modify it under the terms of the
 *    * GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version
 *    OR
 *    * MIT License: https://github.com/ParaGroup/StreamBenchmarks/blob/master/LICENSE.MIT
 *  
 *  StreamBenchmarks is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  You should have received a copy of the GNU Lesser General Public License and
 *  the MIT License along with WindFlow. If not, see <http://www.gnu.org/licenses/>
 *  and <http://opensource.org/licenses/MIT/>.
 **************************************************************************************
 */

package YSB;

import Util.Log;
import Util.Sampler;
import java.util.Map;
import Util.MetricGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Constants.YSBConstants.Field;
import org.apache.flink.util.Collector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

// class ConsoleSink
public class DroppedSink extends RichSinkFunction<Joined_Event> {
    private static final Logger LOG = Log.get(DroppedSink.class);
    private long count;

    // contructor
    DroppedSink() {
        count = 0;
    }

    // invoke method
    @Override
    public void invoke(Joined_Event input, Context context) throws Exception {
        count++;
    }

    // close method
    @Override
    public void close() {
        LOG.info("[DoppedSink] dropped tuples: " + count);
    }
}
