/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package block;

import static helper.Lists.list;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bloctesian.Block;
import com.bloctesian.BlockId;
import com.bloctesian.BlockType;
import com.bloctesian.Logger;
import com.bloctesian.Rotation;
import com.bloctesian.Timer;
import com.bloctesian.Vector;
import com.bloctesian.stream.Stream;
import com.bloctesian.stream.StreamBlocks;

import helper.CollectionObserverDummy;

public class RequestBlockData_Test {
  private final CollectionObserverDummy<Block> listener = new CollectionObserverDummy<>();
  private final Logger logger = mock(Logger.class);
  private final Stream output = mock(Stream.class);
  private final Timer timer = mock(Timer.class);
  private final StreamBlocks streamBlocks = new StreamBlocks(output, timer, logger);
  private final List<Byte> emptyRequest = list(0x80, 0x01, 0x14, 0x81);
  private final List<Byte> errorRecoveryRequest = list(0x80, 0x81);
  private final List<Byte> emptyResponse = list(0x80, 0x81);

  @Before
  public void addListeners() {
    streamBlocks.getBlocks().listener().add(listener);
  }

  @Test
  public void a_request_is_sent_when_started() {

    streamBlocks.start();

    verify(output, times(1)).newData(emptyRequest);
  }

  @Test
  public void the_timer_is_started_in_the_beginning() {

    streamBlocks.start();

    verify(timer).setTimeout(anyInt(), eq(streamBlocks));
  }

  @Test
  public void no_additional_request_is_sent_when_data_are_not_fully_received() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80));

    verify(output, times(1)).newData(emptyRequest);
  }

  @Test
  public void an_additional_request_is_sent_when_the_timeout_is_up() {
    streamBlocks.start();

    streamBlocks.timeout();

    verify(output, times(1)).newData(errorRecoveryRequest);
  }

  @Test
  public void the_timer_is_reset_when_the_timeout_is_up() {
    streamBlocks.start();

    streamBlocks.timeout();

    verify(timer, times(2)).setTimeout(anyInt(), eq(streamBlocks));
  }

  @Test
  public void a_new_request_is_sent_when_a_message_was_received() {
    streamBlocks.start();

    streamBlocks.newData(emptyResponse);

    verify(output, times(2)).newData(emptyRequest);
  }

  @Test
  public void the_timer_is_reset_when_a_message_was_received() {
    streamBlocks.start();

    streamBlocks.newData(emptyResponse);

    verify(timer, times(2)).setTimeout(anyInt(), eq(streamBlocks));
  }

  @Test
  public void blocks_can_have_negative_coordinates() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0xff, 0xfe, 0xfd, 0x00, 0x81));

    assertEquals(1, listener.items.size());
    assertEquals(new Vector(-1, -2, -3), listener.items.get(0).getId().position);
  }

  @Test
  public void no_error_is_logged_when_receiving_one_message() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));

    verify(logger, times(0)).error(anyString());
  }

  @Test
  public void can_receive_a_message_in_multiple_parts() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x03));
    streamBlocks.newData(list(0x05, 0x07, 0x03, 0x81));

    assertEquals(1, listener.items.size());
  }

  @Test
  public void when_I_receive_no_block_then_nothing_happens() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x81));

    assertEquals(0, listener.items.size());
  }

  @Test
  public void when_I_receive_one_block_then_it_is_added_to_the_list() {
    BlockId block = new BlockId(BlockType.Block4x2, new Vector(3, 5, 7), Rotation.Deg90);
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));

    assertEquals(1, listener.items.size());
    assertEquals(block, listener.items.get(0).getId());
  }

  @Test
  public void when_I_no_longer_receive_a_block_then_it_is_removed_from_the_list() {
    streamBlocks.start();
    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));

    streamBlocks.newData(list(0x80, 0x81));

    assertEquals(0, listener.items.size());
  }

  @Test
  public void when_there_are_to_many_blocks_on_the_base_plate_an_error_is_returned() {
    streamBlocks.start();

    streamBlocks.newData(list(0x48));

    verify(logger).error("to many blocks on base");
  }

  @Test
  public void protocol_specific_errors_are_returned() {
    streamBlocks.start();

    streamBlocks.newData(list(0x44));

    verify(logger).error("protocol missmatch");
  }

  @Test
  public void a_error_recovery_request_is_sent_after_receiving_an_error() {
    streamBlocks.start();
    verify(output, times(1)).newData(emptyRequest);

    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));
    verify(output, times(1)).newData(list(0x80, 0x01, 0x14, 0x00, 0x00, 0x81));

    streamBlocks.newData(list(0x48));
    verify(output, times(1)).newData(errorRecoveryRequest);
  }

  @Test
  public void a_error_recovery_request_is_sent_after_receiving_invalid_data() {
    streamBlocks.start();
    verify(output, times(1)).newData(emptyRequest);

    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));
    verify(output, times(1)).newData(list(0x80, 0x01, 0x14, 0x00, 0x00, 0x81));

    streamBlocks.newData(list(0x07));
    verify(output, times(1)).newData(errorRecoveryRequest);
  }

  @Test
  public void a_error_recovery_request_is_sent_after_a_timeout() {
    streamBlocks.start();
    verify(output, times(1)).newData(emptyRequest);

    streamBlocks.timeout();

    verify(output, times(1)).newData(errorRecoveryRequest);
  }

  @Test
  public void a_normal_message_is_sent_when_received_a_valid_message_after_an_error() {
    streamBlocks.start();
    verify(output, times(1)).newData(emptyRequest);
    streamBlocks.timeout();

    streamBlocks.newData(emptyResponse);

    verify(output, times(2)).newData(emptyRequest);
  }

  @Test
  public void when_a_incomplete_message_was_received_and_we_run_in_the_timeout_we_can_receive_a_new_complete_message() {
    BlockId block = new BlockId(BlockType.Block4x2, new Vector(3, 5, 7), Rotation.Deg90);
    streamBlocks.start();

    streamBlocks.newData(list(0x80));
    streamBlocks.timeout();
    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));

    assertEquals(1, listener.items.size());
    assertEquals(block, listener.items.get(0).getId());
  }

  @Test
  public void when_a_incomplete_message_was_received_and_we_run_in_the_timeout_an_error_is_written_to_the_log() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80));
    streamBlocks.timeout();

    verify(logger).error("Incomplete response from the hardware");
  }

  @Test
  public void when_we_run_into_a_timeout_an_error_is_logged() {
    streamBlocks.start();

    streamBlocks.timeout();

    verify(logger).error("No response from the hardware");
  }

  @Test
  public void log_to_debug_on_receive() {
    streamBlocks.start();

    streamBlocks.newData(list(0x00, 0x10, 0x20, 0xff));

    verify(logger).debug(eq("received: 00 10 20 ff"));
  }

  @Test
  public void log_to_debug_on_send() {
    streamBlocks.start();

    verify(logger).debug("sent: 80 01 14 81");
  }

  @Test
  public void normal_execution_with_2_messages() {
    BlockId block = new BlockId(BlockType.Block4x2, new Vector(3, 5, 7), Rotation.Deg90);

    streamBlocks.start();
    verify(output, times(1)).newData(emptyRequest);

    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));
    verify(output, times(1)).newData(list(0x80, 0x01, 0x14, 0x00, 0x00, 0x81));

    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));
    verify(output, times(2)).newData(list(0x80, 0x01, 0x14, 0x00, 0x00, 0x81));

    assertEquals(1, listener.items.size());
    assertEquals(block, listener.items.get(0).getId());
  }

  @Test
  public void the_timer_is_stopped_on_stop() {
    streamBlocks.start();

    streamBlocks.stop();

    verify(timer).stop(eq(streamBlocks));
  }

  @Test
  public void stop_clears_the_block_repository() {
    streamBlocks.start();
    streamBlocks.newData(list(0x80, 0x03, 0x05, 0x07, 0x03, 0x81));

    streamBlocks.stop();

    assertEquals(0, listener.items.size());
  }

}
