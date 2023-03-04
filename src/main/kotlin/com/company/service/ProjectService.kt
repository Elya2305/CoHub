package com.company.service

import com.company.domain.IdResponse
import com.company.domain.ProjectRequest
import com.company.domain.ProjectResponse
import com.company.domain.ShortUserResponse
import com.company.domain.UserContext
import com.company.entity.Project
import com.company.entity.ProjectStatus
import com.company.exception.EntityNotFoundException
import com.company.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userService: UserService,
    private val tagService: TagService,
) {
    private val PICS = listOf(
        "https://lh3.googleusercontent.com/VsCsVB9_zfzZxVTwMlbSciWcJDhA3J33FOrUD7ryGUM_ssuKPycGpVc4P0dssHR0Bkl6OxVqc9LtiXHZhGsFXOYz0oBzUqplvT_6jvyZkoQWrAxPMNzzL7tksGcB9tSv_DnzQbEhU8TctT40-pkmrDAHvftzwACR3TmdnXjCSQK-yjkMKyUeLZKwlq999spCX9kibkXAL1Vza2uX39DibDWrgKow_z23lFcRUeBPGQ_C-JdegIHnUpeaMXWnw7yEnzzUJv-Kcw-VuorHihPiWznDaGH1PIcVmu5aUYj3FHWSSE3ZRnwUzKS6Uri2Hf8iu-IRw1r-RWvf_BcRuchJylbq_Ymrc0_EVlv8JcyO9cFD8U_SMHOkBdjHLme_hrDGWkOnOYkbRTI2pGxMNOCTBUHQowcM6WRCzzzSz50SKMPRIcEVGK9TS043Spj7-alBg2I5TY_gNPm_v_EsFYvKu7UajjX2OSCP54aVTjrE886QsMImFX9LaQOnIaPyw-HVgZn4ZznsgMpuC9clFYLEI1-1p-bH971NfZmkqOQtePYSjXIisD2MWb9LSGFmmkpwmYGOrJD2Ia1AeOmddi4U87uphJQXdQjzlL9h-BaS9aT8rDH5htbpN3ucIYvlO7-3-dORn62jF9UNO4uePIM0ZBTFpKEwussCQjghK_fPrpZKFZohOABYP-UfzQcLQfpu46-AWOX_ZLa2vxu_-NjZOnh9gB0W9Sh3mHXYTNLHnRrbReL_L9Ef0z9K3KANiT30xYwwdUZEeVOInI_E2oFUG6cSH0D71lll8sjm-zHoh6Qvq2XCbWAbcxoQkeIYL6ONet2KM4Xc8alA90uhXnjAylEZ9_NKl3uWhTRBVo-C3dy8oeL-gY1e_SiMFokx8_deOZGxn0V22AVZ06St5KxyUjoEnWQKP5QkZ4nGRLAc5fQfbrd1=w2920-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/zh4MqGEStSzys4h8NTzGobBcfkNGH_eYDBktn7ahS97wTtqOU_TkpXQrMu3-GzCYMFhbcLv19iAob36HKikJCTQbzdrX_coFxryr9ywV5xqbu8lmtnvjFVmnJNk8-VjtT_LpGXL2SSapiahF5xSaniTxcns8eWO3krA2u_z5Ijd9m7NTlbeTicWnuHbCVK4MvOjcPbtEsQmOo4gp761KWokOsiJ33LMW3cU5xGPZA6QDGbDvBsYwZkIPFNO-WQH61dCJmMr1jjw2SaLeB9X27ZqFnjgkWB48ElFsGR41m-fM9uWzGgcZkC4ZpPCrXCvrdNdu-3gGA8K-k3BQ_ndQAwFV9OSGTJEPLyXwsvElbIKR8BT1XQsGM7K36jqhvB9V5pNLmVOZikoVk4-hwSIywuhqE4aK_Gbso_EY94udxHw7rXrI72_wcB4miHdUizCS_ZpxLry-VXUE3Tqy0CM2wv_sv9kC95m5ZO5fhoq5EiQys0kkiSxzJC3vNjgz4yGHtweLWlRNirYMbqPnpwBJtOQ4WLU4qFjB5vQwDGxsVdesHmA6bKxG068FMc2yF8ZUgksOlhnF1XvJgMkIZOueotFVtc4qrYl5A6fdwAM0uWQ22xh0xWbgHzYIT33AR2aUdOSZzl6Aqf5UDKcSmv1zlKYLOgkAbdR6YtH3NrlGwgXyORGuLLLKunJRYLMQEYxnL_4o_DEgzjKqyyB8P_5R7hcDQfpMOxg9K0JanKRyQeIXEtFBkPgtsyqPh4fsiT-gbY2YVzs0eoXIQaeMIALMn0dlTq0Y87bs_f__VoxjBswx2oQUhVMNS1dOpEKPzzo3vdKja6nbB9E1bzGL7k19NoexEMRA-75ZBTcYd_9oiKf275dtkxhABLJ7JS5tmOefPb75XfDs27Zm1CjHYEV0zD5RPkXmo7LO-eVyQqA0N8rNh0Y_=w2736-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/V8MOyl5XnHGoAUiNVG52_BCsfUaeQbbqE2K6N6u_tdALiBrGQlI5VA_t_sknZwMaGerLiQV3lJCT1DJnDJcMOL0KFwTZ8NU1uaFujGgORcC6co1Af18g_JyQAog21JJCdAC-DosRaR87miNsOrStWzp-VGYUq788MVgIFo-Rbbu3N8A42gpJht5396cpVJ7MoIl_MO7Tkhjcs4Hk2xD6InO53XPJJH4KeP6fu2GjXstLDHIzGbMCBFixv8zFWdD0zSLRK3AoKUFhiHFaIdB8UArO9Asi6eVk3x1cfLffDIG6QEdqj_UJWwkW7bWAtu5MhQt2uk5fDNKypBXTjswthFuN7nDmIh1BhVLbKXP-nImtkv21wOQ7S21LlkL3ZfI_5wehnxCiOZmplh0Hvhi3A1MvVQgmdmuzMVWNNErBR5lkNm9eWo895dBUJYOXyRIAHNfAung07flKqjdFyZp4KfpczzjWFLYYlyZ101QkJBTOuCIxlRUE5bi_FIhGi9iJHluWx5NSng8-0swLifoSGGLk_3DmFZ7qlB66qw0Q4ADdQFtczno3JN7ERaBh1jADez3Pl0LT2QkDcPWeoxLO6yrO51ohtE01JjSmMy2hs56EYj1fZRq1IADiDF2DIXz6fODr80h_x8u9ccXt0rC7XuZ6S5PgCsXj16X5HS3qazXkdyvHtzEL7bl3H2uS220d9w_t6gFwt_Mb-iL302PDB_u2HhhDLwI82xG4h9OavHX5sI_Sq6SlYUqjJmaaBiQWVrF-_c4CA51WER5ACbmp0ZAZa4f14bHS9SdnjPgbrNVYFqMFtgmOynyIpcyHx1AF8XXSpuY_BOnmqlhmOYyUQMleP8mB7Q8XwScqdHzXJGL913DhlU3gUPluBEr1eez9ysbsFAgXKNBEKx6IgDA8FxmbgyHQH-0AzdsgJksTkiCpzv-m=w2360-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/obkoCfEGSHck4ijIQAeGH-BtFV4gCAad1nIPXgNB2945AXvYUwicTVnN7zmpz8fEmqrXZzTJ5pLcw0KnKa708XxLP35i2K7xmkH3QlohH7EbLPIxiHBgV0g2w0GiJiUkfCUB7eNB0wnsmr7eGd3tSqFaveid56Wlh1Wd3sQNPP1ZAsNHa9QYBSafcsCVbPGcM0QjAjplbvv6sAtewJX9U96U2XkOLF1xkHXY7NUqAjX3V3UfBjylgxG8wOmDx6-E7esfpmabYRd3LZrr2hbDKiyjloOYYDa0Qwgd_6UfIO6DvrnC_ajTjgJgFputk-aZWEWmJvs1xU8a8AnIuZC7jABdjwolgQCrKTlVUDJNi49no4P35zaJQnUrNQDB9-5Q84QHIxNO5tZJ5MhlQ63p-1s7zi8IDUj9zVh03VCa1PWBNFeLf6oBCzI4cSH4DXD2oeAgPE6hoTYBAAo9DJ7WnnhKPcKbPj1QGq08tliBmvqw8AUNCOmxO4q7OoBsjy1A8aDDrKgxRgGKsd4H2ZfZ3qUeKXbL0qUYXxMQub-RGg0mqyHh84vF_Yn-0llc3z4izxewGW81DLBLMItj0KSOT7hB6NzUXFGBlIdyZ2mFFJ7QIgUxrw5YK5eT4tZZ0cLExl09EWeE9dnAdf-xIwerZmHeUYuD0JdTlPV2rQIKr-OAvnFy0hCsw7fcoioCMSxJKPcc3U-XSmgJ5jx3ujpQLJWT903D91Ecw45G0-ysoGt1Bje2VDwMCL-2vQIUxESeqfYezHGwAYJKPWg1ffO3oR1HUSU9kt2aZSDk2BXuFFGhtFWrUwd5whJ_-UYWkG8C6oeyyvu5t5Hl0By8fe_N5vDKexeMOpiW9BLb78x54xuuh8UlWJKXTz6eSvElMe9QIMU5FDkNVjQllpRrYg0AIbL-R0AZWYXueREfq44n5e6cPbdp=w2736-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/5MdF9bRjnA3Kx_4GHMuE-j9z2FHOjXf9G6wTMQwxU2Baequ97RnqPSJph7UMY2-mi1uCu8IRRnXWLXZHt7kx6gp3_2STvocum09r9ODkz-8V3DHLpbWZzZ9BAj_MyiiWTmjcz2apDKxiINZjGn2RyHDM_Xizg8inKs905pXa6pJI5LVbFxWlFsceXXI-qxMYTUzeZKtxCDMyYICoR_WjHFUeZNtVSepCVLRBSyq2BRObPdylg3nBbabakz9CkzF-Iff3tShFa4hlJbKiboaEZkebwCRMhgYL_oFUS4Om_yPeBTG5pIUqccvCqd5bpmFMC_iDJ-6R1KuvwkT1ol9LkGbHCGJkCZF6WAYjQWBvQG10fFnk3o0_eD3M4UcQXJHkwq_7rj3nzpLiFwqJq6iz1846mA615P68xn6SGuYaUfxxSFXlngRLLyz2wi55QbsX1vD5EmY3AAnSsKmgSnuwmA8p3PZmHltn6eY9L9u1TwwN8p0kwmer6298sReJcc6xkJe7hsvwr5IzHxf4LRAhAAusU9z9xd6BbS3ZwGBrN7RL7flkLELTsmVFepVvWYIOJ6HrFW_UusK324g65bqMFLTb2TcUxCCWnJV5b0k_sZ5aBDZPLWZEJji-fKTajueYyN9-pLS7IsR45AuUcq_dKtFTiC0wCE2mGeRYxs8faEAB7Nck-9sHlcqWEqvToVFRe98DtvXMC2E0UTdoVAfQDC77o0v4s_5T7b5UcU6j4MZTilWKJ-EjDAR5M5k3jGz2PsR2G9Eab8RUV4dUbWxB7Z8D9SUJmWYqsN3axEnd7csm38TXrUkuJWcGbqhLYE1StdNXprDYlW--dGZ1zli8n_a71F8a4Txm-S_Cn0z4hkpPA0rh_kgCvfYwoL8U7I6szQq7BjT3pAH62T6Tmb1v4xGrWAWoLgDDlLoxNesJGuULK-7g=w2736-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/Hl9vFQit0bCYz8_n1zN-4mlJrnqTnSIsLf5Y2lCt3YiWqBbi7WbVQGLiJXOtV5RKMIgBKq_UHnmUbmh51XlYkajwWAxvmEdho8v948yA9BMipQDUDVAK_mVL23cyMuS_vG_O3VgZF2lbxFSzHv7BdoS0-w2usqyvSaVzTAxc7RDxjvKDAHsRT_05ZcROOznI2CgbTY4CucrqD9WnhY_D0F1cCCz02ykVb9HUZsJtDnx7z5YM3GK4sY67uYKEO8YKklAW1uxN0Y_ft8_PhKZH3e9I36XV6yyIZmah36V4mddDnwBdb1r8A8i_QLozMlBGM0T6dWGAaxC_QFmCgx8fzxwX6bT5sU8Ox4c4nV7q8eLJ53qCXiZzpABCVOs0I89WOCpQGRJ-WrGg9JNd2u_07HMTF8_7aHtRu_8O_R0HJFisyR14T_GrYE30e5T27dPkR5Y1Im3LbKQ-CNaRM2NGyodEOWDfoNYauOE0naxnCn352nojU_OQpN1Dy7J4GrC5qUz6-pJItV4Y_LW9lEnPPG8dxflAo5Pm3wPrEg3-jP_YJi-Ec5E-PCOVAV6k0d4AYLSselOFppYMv8Pm5Wr-JYa-xtUR3oOuO3BWwR4SrshQRarqxc489bdpN3D9vIMwahfGHOqL0TiNLxnzNDMl6DBWdbalXMoH45eFieuKkEffa_cF2rgCDPlwwx-WHak6le54NfIJKc7i8NwXCtrU5gJHSVbJxMXlJ3W2X0n3_fpSwfqDAmvckj-_YlI-fe4wuOHQdDWQ7ZJhOQr3dCIz8CFbq4kaqDGkNHMqCFfUy-aEXjAus12pjhkZ4MR6IT6I7xFaXGfZ9ugkMYDMZEvDYNLShns29byWpLMHTVT7-3F76FVviQA1VoBWN0rHJmrxG8IpD1s4dZz0O_J2ucshk9VWgK9XdbyokDeyUyRNI8rHxBXf=w2432-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/vsxjDUvy9bV22w1wot1UxBh2W7yn7bdlPZwlvCjv5Gr-x8Ltx5XOS_y8JYnEYFQ_zJAOYPkAAc-jyOSe30SoIQe4fdLjtufDj77iCBNsOPlJpKogXTagBdDxfoijBQphuv869KT3c0pYBwYX-7ninW3-HurYQuvwaPV5gG9nxdBscDM1rwxZL6tG4GDpefld3LCG2rI_L4QrCMis32_gz3AiTZmL4RubtAgmWaxclYQ_Ar238-0QcOeC_linUnd9wzh2UgrXIyCyYfd8zconKTWWD-5LYErHyWJptdyJzZi6mXq1JxLllHLb45kx6CLq6S0ByLM3EccVr66MyoQNJeI4XbdIJNpgEf4kKyeE7HreJ9IIxvPFk2sq3Rtn_uxODn1TzjzDxlYId4Qv8NGxyAIvOPm0-Z0G5yOyHaCbg0aZ0MwT-qerbpe2G1j3YwacI_6AwYEVpLlTYsQp5TcodKpjbDZFuqgDP6I2ysdz07liHE2T0kd3-kRgT7V_AFP7Mwk_Cy85CSeq_3XVmdVb0XHOLtEcYz-IQPex0m2dHMIFWivzsGVxRAkoK-VPN_cGLN4B3p1vQmhP_G8feAfurnQmYLKXX7w2fL_IBEx3Tl-LIPiQpNAtmTJuo0hkP_MyWklgUglx2qSRSz0hZwu53wnATODA5gbrD1aBYt2LtyTUZ4NPSbXYzbMORPnQOaL4WT-ZC7ApnpduzL2Jt4p8R_jxdoyS9h79IIpJGYvWeMO_Q57MtxcCMIutI8AQ5FPD13guNUnsMtg-oNAQuFl8V040KrMKZZ0ydSd_fdMAfS5FLPqp4xL-2uahHPJo1McW9sWYoyCDRcXeBOVZkTOFNWTd3SIjjftUOwVxR6VCa2ZMaoOq19rHcx2zftrR7YmG9uotUZK8zyYDjQVzlMD3lEpw_8iBNqbCQFMT79PvtEJjO3NT=w2736-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/z3nggBWeHUssh2X7dsTAzAtPbl3uTGDnPa2V4z0zy268NNO-XFOZ0OU7coHtOnXmhpUHT84BJYXG6ODICWvC9JvC0nf8lKH5dZTlfHDtwqogbuaD80pdcg_3s-T9twn65uZK-Q9Q0hLpDjzqnK_3gPPn2HxmeFU50WCnZFI4ZkF1LO0fOulmtEPA-WdBzzbbwnGLqetUs5oHFf5_c78kUWV6Vj2COcyBd-Wb1AtgIlug9lc8kFLKXTPMmbvmoC9eBLle8VyQD5dqF2InLmNUjYCW5W-s7wUZNoOMCWCbi0Jaz0vsQKVmFsFS5FHP2I_RzkE2hl0dhyzePaRmvSkarA32-6a_oO6B2MeTdzWzorYOjs4YgU3kjbepMbVjNLco_opH0Oxc4jJyQdn-36A4K5DTmka_gwu_HbnXcpnokrUz3sMOdC4ccwtp5-Ed0ikf2W-3Dk3rKlWcv9mZ4F2QDEG8pKh4fxO6xfxiyHR8KHoal89euOmuuNmpH-cKiudnQ1Ybpdsg2zTuq-5eI07nhYkEGmmcJfUjJu3hODnoAlw-VM9oKkjTENil8skPAxv_ib_2URV0DtIx37GQLDxKP43mznY-ByS3QOORq-I1maPp6gl0kOk8x8hhnGOBj4jCsIIJ75wn917gmN_1jt-eJ21dzBltmnm1jWHaofC4bqZxC15hYo00-1WbTtSH38MU_veadrP6ygkK3_xzSj28bGLxPWZmZ14GBqCAejj4pPTmJwcpUrD6HPJB2IatJKG4fysQhUZEv33B9maMwDjquggLXMSlvvvukySEFzoJU0zVj6oufZxqG17DA9rybD5dr9cukMSlLAPy8KtB6-jBDR9OLMfUKZOdQsIBk2UOq4eIXQz4NHwCQRzEI5hSPPFPPo1LY4YBkHfHvFnpZhBHmhVw0ff2oSDVbktlWOtjSKBRYqAP=w2736-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/tK-lxZUEg3BGL7D24lFc4Evnwhl1uOroWCPjMvs3n8qXxgZu_lAZcH0vZGiuoq7idlusAV7zVHLVR0FA_4_obNy5SxELkDtkgDNNcT4X1nO4VX69Gqo566WgoOCRtSoti9DXqFuIXHqtdvSisodr4cCD6Nddo3g71pHK8Fs-SwMKpgHRQBz5n0wB1tlaqVZyQXJhMMfb7wOLAhuoLRbr8wNI5XLP6_hYMLdQ8-K7_nieF2NLLLBEYatzXGT9ArLA5n12zOPft71uQnEBtqsSCh2ypmSY-XYBGR1eosf89MtD-Vm2z-UvoeGRxpyJySwG-bi4jcO8DJCo9oYc-p5pcXMgixX2sj-EHdOQHPpS8Z_FDzsdU81eQry9_WVvoBZGHoVUxU71eWl5uggrUv3OaV8YQpe8wUfdsPxyvcjW1amMPqTCTzZQUOjOWBdc_gSuvMubpze-06Pjkx3tPov11bxi8mHoUvYmu9nioIa8VTLUPoUGQUv0kLEPapwDSTVHsBzc6HqZnMWv92MJpHkNqIFESY8H6YHNvaZfTWqT8_UT4qrrlhIgv1fA0p-4t5ykJ2SCua7Lh0B9COOTlWnIh-upF-li25wmQyaDYQN89uxViMimWzXNTQwT7P-XDw9CCJ7IqDWpLlXjuTpvsekqR-WLt6ML7AbjYn08dru611rYfO8oc2cHG9MA7C657C9XYbOoZdmrNgAqh0gVHOiy9EooWYg1zZQ2Q-4XkD0GTwzcRWmUms2uK-7hDhApw1pGvaAUnDZoXFkUV0BriptXw_mVLZuJzJOWxRdr0CgCFeUpdm9toZ25ydCSbGaokPiCUmHy37LiwnqFqOFpDJM697rDs6Bn-zf3-6c1oBYiEATcht20WMnNzvB58wfBbkmmf55MgqF_pIDR-CJQqgfUyGriaTC2Zl4XYu2epSZ8mhhHNybV=w2736-h1824-no?authuser=0",
        "https://lh3.googleusercontent.com/cpi-FB788qVqcysqSvS1kvhSIoW1rSk7k5jNh1aS7ZrfKOAp_DVl_PA5Ep83VIHQ_2zkdCHktJhnKCMrt8RdKwfKXAICc_2Y3TKU9ljs3oODaEjcy77o2rlrZ5dm09rqJH8E5DGhdqs_sAtGBzqVUxYDcuACw0MYW9Rl2u0sWJlgZF3qj_xyKTzsiF8YfrQhX6gprEBtr9x9fs1HtlJCDP4mxHgOIG5vHa-UJV_Y-71ylkS630eC6cNreJK003Rv-xDGxGf5PemM3WQz_c-7c1dTL4hi40WMANNjhxI-Lav_c09YQ6hcSktm5A5N5yXdXN5fJRFdd8h1Lsb8DBpIMqduzwmaDUXrxp1y31Zspk9ZPWDkZI-0TVOqzAhA-YVmS30GfmCiwxqTOwWRkffYBb6SJY_I2l7fdt8TH2THlKMNKtgOZbo0eRbz7-rkhuWVK_9rQczRAeHuOgHtJYR5jq_XvvJTz-Aj9Q37kqrOhdj500ZFD5RpN5Co2mwbjercFc1EZrdR6B9M6uzeBLSgxogFaq1XwkHnYGzwMmNSNUH2gmlJE0WGFxRYFtdkTLQKGlCgP2D9lLcqOdGewHy6zNRGU9exG2XcgnMid-jnByLHdEc0Nm1wxy6oGqNuaTM6TSQEa-8qAz5S4TOyknVjFCDpe8N4yZrvZbrUl_HswT4d6L24zxVQZkw4w5pQWx7L4ttgVJHd8kFsGwU-5N0lxKQM0tZgrV09JHimBG93MC3G7zysDZh0lZzT5LqPCSlWL4Q53_z_4TJPq_7Tg64JtEhCq9nF9txpcvuy9vcLUGS0LCRqI2plleLVvjwKXcpiLEyu16EC1c2fGSpOp_FT-eA6wedx6ycnBLU0vKnP_C8fblTVfDGxFnN6f1ll1EGsbI4wj3zXrEoxnfYykD170J5axbkZ8nSKAkAZpjA201spVt9q=w2280-h1824-no?authuser=0",
    )

    fun create(request: ProjectRequest): IdResponse {

        val project = Project(
            title = request.title,
            description = request.description,
            tags = request.tags,
            pic = PICS.random(),
            author = userService.getCurrentReference(),
        )
        tagService.save(request.tags)
        return IdResponse(projectRepository.save(project).id)
    }

    fun get(id: String): ProjectResponse {
        val project = fetchFromDb(id)

        return map(project)
    }

    fun update(id: String, request: ProjectRequest): ProjectResponse {
        val project = fetchFromDb(id)

        project.title = request.title
        project.description = request.description
        project.tags = request.tags

        projectRepository.save(project)
        return map(project)
    }

    private fun map(project: Project) = ProjectResponse(
        id = project.id,
        title = project.title,
        description = project.description,
        tags = project.tags,
        resultLink = project.resultLink,
        status = project.status,
        pic = project.pic,
        isAuthor = project.author.id == UserContext.getUserUuid(),
        team = project.team + listOf(ShortUserResponse(project.author.id, project.author.pic))
    )

    fun startProject(id: String) {
        val project = fetchFromDb(id)
        project.status = ProjectStatus.IN_PROGRESS
        projectRepository.save(project)
    }

    fun finishProject(id: String) {
        val project = fetchFromDb(id)
        project.status = ProjectStatus.FINISHED
        projectRepository.save(project)
    }

    fun allForUser(status: ProjectStatus?): List<ProjectResponse> {
        if (status == null) {
            return projectRepository.findAllByAuthor(userService.getCurrentReference())
                .filter { o -> isUserParticipant(o) }
                .map { map(it) }
        }
        return projectRepository.findAllByAuthorAndStatus(userService.getCurrentReference(), status)
            .map { map(it) }
    }

    fun allOpen(tags: List<String>?): List<ProjectResponse> {
        return if (tags.isNullOrEmpty()) {
            projectRepository.findAllByStatus(ProjectStatus.OPEN).map { map(it) }
        } else {
            projectRepository.findAllByStatus(ProjectStatus.OPEN)
                .filter { o -> o.tags.any { i -> tags.contains(i) } }
                .map { map(it) }
        }
    }

    fun getReferenceById(projectId: String): Project {
        return projectRepository.getReferenceById(projectId)
    }

    fun addUserToTeam(userId: String, projectId: String) {
        val project = fetchFromDb(projectId)
        val user = userService.get(userId)
        project.team.add(ShortUserResponse(userId, user.pic))
        projectRepository.save(project)
    }

    private fun isUserParticipant(project: Project) =
        project.author.id == UserContext.getUserUuid() || project.team.map { it.id }.toList()
            .contains(UserContext.getUserUuid())

    private fun fetchFromDb(id: String) = projectRepository.findById(id).orElseThrow { EntityNotFoundException(id) }
}
