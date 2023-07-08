package com.github.instagram4j.instagram4j;

import lombok.Setter;

/**
 *
 * Instagram4j API
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class IGConstants {

    public static final String API_DOMAIN = "i.instagram.com";
    /**
     * Base API URL
     */
    public static final String BASE_API_URL = "https://" + API_DOMAIN + "/";

    public static final String B_BASE_API_URL = "https://b." + API_DOMAIN + "/";

    /**
     * API v1 URL
     */
    public static final String API_V1 = "api/v1/";

    /**
     * Experiments Activated
     */
    public static final String DEVICE_EXPERIMENTS =
            "ig_android_reg_nux_headers_cleanup_universe,ig_android_device_detection_info_upload,ig_android_nux_add_email_device,ig_android_gmail_oauth_in_reg,ig_android_device_info_foreground_reporting,ig_android_device_verification_fb_signup,ig_android_direct_main_tab_universe_v2,ig_android_passwordless_account_password_creation_universe,ig_android_direct_add_direct_to_android_native_photo_share_sheet,ig_growth_android_profile_pic_prefill_with_fb_pic_2,ig_account_identity_logged_out_signals_global_holdout_universe,ig_android_quickcapture_keep_screen_on,ig_android_device_based_country_verification,ig_android_login_identifier_fuzzy_match,ig_android_reg_modularization_universe,ig_android_security_intent_switchoff,ig_android_device_verification_separate_endpoint,ig_android_suma_landing_page,ig_android_sim_info_upload,ig_android_smartlock_hints_universe,ig_android_fb_account_linking_sampling_freq_universe,ig_android_retry_create_account_universe,ig_android_caption_typeahead_fix_on_o_universe";
    /**
     * Instagram App Version
     */
    public static final String APP_VERSION = "148.0.0.33.121";

    public static final String APP_ID = "567067343352427";

    /**
     * User Locale
     */
    @Setter
    public static String LOCALE = "en_US";

}
