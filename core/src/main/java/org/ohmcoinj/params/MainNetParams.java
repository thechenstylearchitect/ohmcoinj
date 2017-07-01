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

package org.ohmcoinj.params;

import org.ohmcoinj.core.NetworkParameters;
import org.ohmcoinj.core.Sha256Hash;
import org.ohmcoinj.core.Utils;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends NetworkParameters {
    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1e0fffffL);
        // proofOfWorkLimit  = Utils.decodeCompactBits(0x1e0fffffL);  // bnProofOfWorkLimit(~uint256(0) >> 20);
        proofOfStakeLimit = Utils.decodeCompactBits(0x1e0fffffL); //bnProofOfStakeLimit(~uint256(0) >> 20);
        addressHeader = 51; // OhmCoin: address begin with 'M'
        dumpedPrivateKeyHeader = 128 + addressHeader;
        p2shHeader = 8;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        port = 16700;
        packetMagic = 0xced5dbfa;
        genesisBlock.setDifficultyTarget(0x1e0fffffL);
        genesisBlock.setTime(1391393693L);
        genesisBlock.setNonce(12488421);
        genesisBlock.getTransactions().get(0).setTime(1492063422); // main.cpp nChainStartTime
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 500000000;
        spendableCoinbaseDepth = 10;
        spendableStakebaseDepth = spendableCoinbaseDepth + 20;

 //       String merkleRoot = genesisBlock.getMerkleRoot().toString();
 //       checkState(merkleRoot.equals("c1ff0f596b0c49e7a8b7235cddb5f9a29ff6c822a7786556bc49de54aa914740"),
 //               merkleRoot);

        String genesisHash = genesisBlock.getScryptHashAsString();
        checkState(genesisHash.equals("69fc36e4d84f9fadf079e3360d6de92a68192a2f752eb81d88b8473fc5612b66"),
                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.

/*        
        checkpoints.put(10001, new Sha256Hash("000000000844c716892664582ee292ff941798319df7e6ae02be2d56a384f58d"));
*/

        dnsSeeds = new String[] {
                "162.243.110.105",
                "192.241.254.184", 
                "167.114.117.96",
            };
    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
