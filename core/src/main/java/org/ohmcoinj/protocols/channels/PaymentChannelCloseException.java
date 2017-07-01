/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ohmcoinj.protocols.channels;

/**
 * Used to indicate that a channel was closed before it was expected to be closed.
 * This could mean the connection timed out, the other send sent an error or a CLOSE message, etc
 */
public class PaymentChannelCloseException extends Exception {
    public enum CloseReason {
        /** We could not find a version which was mutually acceptable with the client/server */
        NO_ACCEPTABLE_VERSION,
        /** Generated by the client when time window the suggested by the server is unacceptable */
        TIME_WINDOW_UNACCEPTABLE,
        /** Generated by the client when the server requested we lock up an unacceptably high value */
        SERVER_REQUESTED_TOO_MUCH_VALUE,
        /** Generated by the server when the client has used up all the value in the channel. */
        CHANNEL_EXHAUSTED,

        // Values after here indicate its probably possible to try reopening channel again

        /**
         * <p>The {@link org.ohmcoinj.protocols.channels.PaymentChannelClient#settle()} method was called or the
         * client sent a CLOSE message.</p>
         * <p>As long as the server received the CLOSE message, this means that the channel is settling and the payment
         * transaction (if any) will be broadcast. If the client attempts to open a new connection, a new channel will
         * have to be opened.</p>
         */
        CLIENT_REQUESTED_CLOSE,

        /**
         * <p>The {@link org.ohmcoinj.protocols.channels.PaymentChannelServer#close()} method was called or server
         * sent a CLOSE message.</p>
         *
         * <p>This may occur if the server opts to close the connection for some reason, or automatically if the channel
         * times out (called by {@link StoredPaymentChannelServerStates}).</p>
         *
         * <p>For a client, this usually indicates that we should try again if we need to continue paying (either
         * opening a new channel or continuing with the same one depending on the server's preference)</p>
         */
        SERVER_REQUESTED_CLOSE,

        /** Remote side sent an ERROR message */
        REMOTE_SENT_ERROR,
        /** Remote side sent a message we did not understand */
        REMOTE_SENT_INVALID_MESSAGE,

        /** The connection was closed without an ERROR/CLOSE message */
        CONNECTION_CLOSED,

        /** The server failed processing an UpdatePayment message */
        UPDATE_PAYMENT_FAILED,
    }

    private final CloseReason error;

    public PaymentChannelCloseException(String message, CloseReason error) {
        super(message);
        this.error = error;
    }

    public CloseReason getCloseReason() {
        return error;
    }

    @Override
    public String toString() {
        return "PaymentChannelCloseException for reason " + getCloseReason().toString();
    }
}
